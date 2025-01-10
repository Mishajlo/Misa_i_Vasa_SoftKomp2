package com.example.Rezervacije_Servis.service.spec;

import com.example.Rezervacije_Servis.domain.entities.Achievement;
import com.example.Rezervacije_Servis.dto.achievementDTOs.AchievementDTO;
import com.example.Rezervacije_Servis.dto.achievementDTOs.AchievementInfoDTO;

import java.util.List;

public interface AchievementService {

    long addAchievement(long restaurantId, AchievementDTO achievementDTO);
    long deleteAchievement(long achievementId);
    AchievementInfoDTO getAchievement(long achievementId);
    List<AchievementInfoDTO> getAllAchievements(long restaurantId);
    void notifyUser(String email, AchievementDTO achievementDTO);
}
