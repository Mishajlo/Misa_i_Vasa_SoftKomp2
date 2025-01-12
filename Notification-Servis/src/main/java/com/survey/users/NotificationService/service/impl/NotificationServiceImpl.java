package com.survey.users.NotificationService.service.impl;

import com.survey.users.NotificationService.domain.Notification;
import com.survey.users.NotificationService.domain.NotificationType;
import com.survey.users.NotificationService.dto.NotificationInfoDTO;
import com.survey.users.NotificationService.dto.UniversalDTO;
import com.survey.users.NotificationService.repository.NotificationRepository;
import com.survey.users.NotificationService.repository.NotificationTypeRepository;
import com.survey.users.NotificationService.service.NotificationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationTypeRepository notificationTypeRepository;
    @Autowired
    private MrtviMailServer mrtviMailServer;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean registrationMail(UniversalDTO universalDTO) {
        try{
            NotificationType email_type = notificationTypeRepository.getNotificationTypeByType(universalDTO.getNotifType());

            String message = email_type.getContent();
            for (String param: universalDTO.getParams()) {
                if (param == null) { return false; }
                message = message.replaceFirst("%s", param);
            }

            String htmlContent = "<p>" + message + "</p>";

            mrtviMailServer.sendEmail(universalDTO.getRecipientEmail(), email_type.getSubject(), htmlContent);

            Notification newNotification = new Notification();
            newNotification.setType(email_type);
            newNotification.setRecipientEmail(universalDTO.getRecipientEmail());
            newNotification.setRecipientId(universalDTO.getRecipientId());
            newNotification.setSenderEmail(universalDTO.getSenderEmail());
            newNotification.setSenderId(universalDTO.getSenderId());
            newNotification.setTimestamp(LocalDateTime.now());
            notificationRepository.save(newNotification);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public List<NotificationInfoDTO> getAllNotifications() {
        return notificationRepository.findAll().stream().map(notification -> modelMapper.map(notification, NotificationInfoDTO.class)).toList();
    }

    @Override
    public List<NotificationInfoDTO> getNotificationsByUserId(long userId) {
        return notificationRepository.findAllByRecipientId(userId).stream().map(notification -> modelMapper.map(notification, NotificationInfoDTO.class)).toList();
    }

}
