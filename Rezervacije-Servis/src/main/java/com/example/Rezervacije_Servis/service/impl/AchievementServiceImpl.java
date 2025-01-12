package com.example.Rezervacije_Servis.service.impl;

import com.example.Rezervacije_Servis.domain.entities.Achievement;
import com.example.Rezervacije_Servis.domain.entities.Restaurant;
import com.example.Rezervacije_Servis.dto.QueueDTOs.NotifServiceDTO;
import com.example.Rezervacije_Servis.dto.achievementDTOs.AchievementDTO;
import com.example.Rezervacije_Servis.dto.achievementDTOs.AchievementInfoDTO;
import com.example.Rezervacije_Servis.dto.reservationDTOs.UserInfoDTO;
import com.example.Rezervacije_Servis.queues.SendToNotification;
import com.example.Rezervacije_Servis.repository.AchievementRepository;
import com.example.Rezervacije_Servis.repository.RestaurantRepository;
import com.example.Rezervacije_Servis.service.spec.AchievementService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private ModelMapper modelMapper;
    private AchievementRepository achievementRepository;
    private RestaurantRepository restaurantRepository;
    private SendToNotification sendToNotification;

    @Override
    public long addAchievement(long restaurantId, AchievementDTO achievementDTO) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        Achievement newAchievement = modelMapper.map(achievementDTO, Achievement.class);
        newAchievement.setRestaurant(restaurant);
        Achievement savedAchievement = achievementRepository.save(newAchievement);
        return savedAchievement.getId();
    }

    @Override
    public long deleteAchievement(long achievementId) {
        Achievement achievement = achievementRepository.findById(achievementId).orElse(null);
        assert achievement != null;

        achievement.setDeleteFlag(true);
        achievementRepository.save(achievement);

        return achievementId;
    }

    @Override
    public AchievementInfoDTO getAchievement(long achievementId) {
        Achievement achievement = achievementRepository.findById(achievementId).orElse(null);
        assert achievement != null;
        return modelMapper.map(achievement, AchievementInfoDTO.class);
    }

    @Override
    public List<AchievementInfoDTO> getAllAchievements(long restaurantId) {
        List<Achievement> achievements = achievementRepository.findAllByDeleteFlagFalseAndRestaurant_Id(restaurantId);
        return achievementRepository.findAllByDeleteFlagFalseAndRestaurant_Id(restaurantId).stream().map(achievement -> modelMapper.map(achievement, AchievementInfoDTO.class)).toList();
    }

    @Override
    public void getByResCount(int resCount, UserInfoDTO userInfo, Restaurant restaurant) {
        Optional<Achievement> achievement = achievementRepository.findAchievementByCondition(resCount);
        if (achievement.isPresent()) {

            NotifServiceDTO achievementNotification = new NotifServiceDTO();
            achievementNotification.setNotifType("achievement");
//"Postovani, Korisnik %s je ostvario %s sto znaci da ima %s rezervacija i da %s!"
            achievementNotification.setRecipientEmail(userInfo.getEmail());
            achievementNotification.setRecipientId(userInfo.getUserId());
            achievementNotification.setRestaurantName(restaurant.getName());
            achievementNotification.setRestaurantId(restaurant.getId());
            achievementNotification.getParams().add(achievement.get().getTitle());
            achievementNotification.getParams().add(achievement.get().getDescription());

            sendToNotification.sendNotification(achievementNotification);

            NotifServiceDTO achievementNotificationToManager = new NotifServiceDTO();
            achievementNotificationToManager.setNotifType("loyalty");
            achievementNotificationToManager.setRecipientEmail(restaurant.getManagerEmail());
            achievementNotificationToManager.setRecipientId(restaurant.getManagerId());
            achievementNotificationToManager.setRestaurantName(restaurant.getName());
            achievementNotificationToManager.setRestaurantId(restaurant.getId());
            achievementNotificationToManager.getParams().add(userInfo.getEmail());
            achievementNotificationToManager.getParams().add(achievement.get().getTitle());
            achievementNotificationToManager.getParams().add(String.valueOf(achievement.get().getCondition()));
            achievementNotificationToManager.getParams().add(achievement.get().getDescription());

            sendToNotification.sendNotification(achievementNotificationToManager);

        }
    }

}
