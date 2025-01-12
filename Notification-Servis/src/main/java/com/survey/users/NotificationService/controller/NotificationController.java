package com.survey.users.NotificationService.controller;

import com.survey.users.NotificationService.dto.NotificationInfoDTO;
import com.survey.users.NotificationService.dto.UniversalDTO;
import com.survey.users.NotificationService.security.CheckSecurity;
import com.survey.users.NotificationService.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@AllArgsConstructor
public class NotificationController {

    private NotificationService notificationService;

    @CheckSecurity(roles = "ADMIN")
    @GetMapping()
    public ResponseEntity<List<NotificationInfoDTO>> getAllNotifications() {
        return new ResponseEntity<>(notificationService.getAllNotifications(), HttpStatus.OK);
    }

    @CheckSecurity(roles = {"CLIENT","MANAGER"})
    @GetMapping(value = {"/user_id"})
    public ResponseEntity<List<NotificationInfoDTO>> getUserNotifications(@RequestParam("user_id") long userId) {
        return new ResponseEntity<>(notificationService.getNotificationsByUserId(userId), HttpStatus.OK);
    }

}
