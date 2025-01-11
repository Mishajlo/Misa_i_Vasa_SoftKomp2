package com.survey.users.NotificationService.service;

import com.survey.users.NotificationService.dto.RegistrationDTO;

public interface NotificationService {

    boolean sendMail(String to, String[] cc, String subject, String body);
    boolean registrationMail(RegistrationDTO registrationDTO);

}
