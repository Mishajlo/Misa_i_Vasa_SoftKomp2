package com.survey.users.NotificationService.messaging;

import com.survey.users.NotificationService.service.NotificationService;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private NotificationService notificationService;

    public MessageConsumer(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    /**
    @RabbitListener(queues = {"email"})
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
        emailService.sendMail("jnajdic@raf.rs", null, "Aktivacija", message);
    }
    */
}