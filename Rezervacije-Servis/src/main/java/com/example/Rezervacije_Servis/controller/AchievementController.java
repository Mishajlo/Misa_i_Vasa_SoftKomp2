package com.example.Rezervacije_Servis.controller;

import com.example.Rezervacije_Servis.dto.achievementDTOs.AchievementDTO;
import com.example.Rezervacije_Servis.dto.achievementDTOs.AchievementInfoDTO;
import com.example.Rezervacije_Servis.security.CheckSecurity;
import com.example.Rezervacije_Servis.service.spec.AchievementService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/achievement")
@AllArgsConstructor
public class AchievementController {

    private AchievementService achievementService;

    @CheckSecurity(roles = {"MANAGER"})
    @PostMapping(value = "/{restaurant_id}")
    public ResponseEntity<Long> addAchievement(@RequestHeader("Authorization") String authorization, @PathVariable long restaurant_id, @RequestBody AchievementDTO achievementDTO) {
        return new ResponseEntity<>(achievementService.addAchievement(restaurant_id, achievementDTO), HttpStatus.CREATED);
    }

    @CheckSecurity(roles = {"MANAGER"})
    @DeleteMapping(value = "/{achievement_id}")
    public ResponseEntity<Long> deleteAchievement(@RequestHeader("Authorization") String authorization, @PathVariable long achievement_id) {
        return new ResponseEntity<>(achievementService.deleteAchievement(achievement_id), HttpStatus.OK);
    }

    @CheckSecurity(roles = {"MANAGER","CLIENT"})
    @GetMapping(value = "/{achievement_id}")
    public ResponseEntity<AchievementInfoDTO> getAchievement(@RequestHeader("Authorization") String authorization, @PathVariable long achievement_id) {
        return new ResponseEntity<>(achievementService.getAchievement(achievement_id), HttpStatus.OK);
    }

    @CheckSecurity(roles = {"MANAGER"})
    @GetMapping(value = "/{restaurant_id}/all")
    public ResponseEntity<List<AchievementInfoDTO>> getAllAchievements(@RequestHeader("Authorization") String authorization, @PathVariable long restaurant_id) {
        return new ResponseEntity<>(achievementService.getAllAchievements(restaurant_id), HttpStatus.OK);
    }

}
