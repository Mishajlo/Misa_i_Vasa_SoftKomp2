package com.survey.users.NotificationService.controller;

import com.survey.users.NotificationService.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping
    public ResponseEntity<Boolean> sendSimpleMail(@RequestParam String to, @RequestParam String subject, @RequestParam String body){
        return new ResponseEntity<>(emailService.sendMail(to, null, subject, body), HttpStatus.OK);
    }

}
