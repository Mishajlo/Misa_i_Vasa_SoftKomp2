package com.survey.users.NotificationService.service;

import com.survey.users.NotificationService.dto.NotificationInfoDTO;
import com.survey.users.NotificationService.dto.UniversalDTO;

import java.util.List;

public interface NotificationService {

    boolean registrationMail(UniversalDTO universalDTO);

    List<NotificationInfoDTO> getAllNotifications();

    List<NotificationInfoDTO> getNotificationsByUserId(long userId);

}
