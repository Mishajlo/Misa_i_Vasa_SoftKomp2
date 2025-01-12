package com.example.Korisnicki_Servis.queue;

import com.example.Korisnicki_Servis.dto.RegistrationMailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Slf4j
@Component
public class QueueSender {
    private final RabbitTemplate rabbitTemplate;
    private final String notificationExchange;
    private final String notificationRoutingKey;

    public QueueSender(RabbitTemplate rabbitTemplate, @Value("${notification.exchange}") String notificationExchange, @Value("${notification.routing-key}") String notificationRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.notificationExchange = notificationExchange;
        this.notificationRoutingKey = notificationRoutingKey;
    }

    public void sendNotification(RegistrationMailDTO request) {
        try {
            //log.info("Sending notification request for type: {}");
            System.out.println("STIGAO DO OVDE!!!");
            rabbitTemplate.convertAndSend(notificationExchange, notificationRoutingKey, request);
        } catch (Exception e) {
            log.error("Failed to send notification request", e);
            // We might want to implement a retry mechanism here
        }
    }
}
