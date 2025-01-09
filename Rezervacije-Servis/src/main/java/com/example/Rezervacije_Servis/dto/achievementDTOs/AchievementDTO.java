package com.example.Rezervacije_Servis.dto.achievementDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AchievementDTO {
    private String title;
    private int condition;
    private String description;
}
