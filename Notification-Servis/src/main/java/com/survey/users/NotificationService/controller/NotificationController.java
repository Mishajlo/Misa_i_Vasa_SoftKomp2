package com.survey.users.NotificationService.controller;

import com.survey.users.NotificationService.dto.UniversalDTO;
import com.survey.users.NotificationService.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
@AllArgsConstructor
public class NotificationController {

    private NotificationService notificationService;

    @PostMapping(value = "/registration")
    public ResponseEntity<Boolean> confirmRegistration(@RequestBody UniversalDTO universalDTO) {
        return new ResponseEntity<>(notificationService.registrationMail(universalDTO), HttpStatus.OK);
    }

}
