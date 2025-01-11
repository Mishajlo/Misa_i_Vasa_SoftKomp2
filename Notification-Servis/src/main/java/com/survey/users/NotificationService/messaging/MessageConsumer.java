package com.survey.users.NotificationService.messaging;

import com.survey.users.NotificationService.dto.RegistrationDTO;
import com.survey.users.NotificationService.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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

    @RabbitListener(queues = "queue.registration")
    public void receiveRegistration(RegistrationDTO registrationDTO){
        System.out.println("New Registration Received");
        notificationService.registrationMail(registrationDTO);
    }
}