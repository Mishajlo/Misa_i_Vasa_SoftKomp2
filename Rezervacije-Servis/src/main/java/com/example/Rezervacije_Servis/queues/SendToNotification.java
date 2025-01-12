package com.example.Rezervacije_Servis.queues;

import com.example.Rezervacije_Servis.dto.QueueDTOs.NotifServiceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SendToNotification {
    private final RabbitTemplate rabbitTemplate;
    private final String notificationExchange;
    private final String notificationRoutingKey;

    public SendToNotification(RabbitTemplate rabbitTemplate, @Value("${notification.exchange}") String notificationExchange, @Value("${notification.routing-key}") String notificationRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.notificationExchange = notificationExchange;
        this.notificationRoutingKey = notificationRoutingKey;
    }

    public void sendNotification(NotifServiceDTO notifServiceDTO) {
        try {
            //log.info("Sending notification request for type: {}", notifServiceDTO.getNotifType());
            System.out.println("STIGAO DO OVDE!!!");
            rabbitTemplate.convertAndSend(notificationExchange, notificationRoutingKey, notifServiceDTO);
        } catch (Exception e) {
            log.error("Failed to send notification request", e);
            // We might want to implement a retry mechanism here
        }
    }
}