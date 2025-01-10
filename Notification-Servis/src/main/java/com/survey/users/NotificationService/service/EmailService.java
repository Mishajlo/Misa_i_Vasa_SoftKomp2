package com.survey.users.NotificationService.service;

public interface EmailService {

    boolean sendMail(String to, String[] cc, String subject, String body);

}
