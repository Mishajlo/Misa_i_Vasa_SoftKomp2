package com.example.Rezervacije_Servis.service.impl;

import com.example.Rezervacije_Servis.domain.entities.Achievement;
import com.example.Rezervacije_Servis.domain.entities.Restaurant;
import com.example.Rezervacije_Servis.dto.achievementDTOs.AchievementDTO;
import com.example.Rezervacije_Servis.dto.achievementDTOs.AchievementInfoDTO;
import com.example.Rezervacije_Servis.repository.AchievementRepository;
import com.example.Rezervacije_Servis.repository.RestaurantRepository;
import com.example.Rezervacije_Servis.service.spec.AchievementService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private ModelMapper modelMapper;
    private AchievementRepository achievementRepository;
    private RestaurantRepository restaurantRepository;

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
        return List.of(modelMapper.map(achievements, AchievementInfoDTO.class));
    }

    @Override
    public void notifyUser(String email, AchievementDTO achievementDTO) {
        //TODO ne znam sta je ovo
    }
}
