package com.survey.users.NotificationService.messaging;

import com.survey.users.NotificationService.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private EmailService emailService;

    public MessageConsumer(EmailService emailService){
        this.emailService = emailService;
    }

    /**
    @RabbitListener(queues = {"email"})
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
        emailService.sendMail("jnajdic@raf.rs", null, "Aktivacija", message);
    }
    */
}