package com.example.Korisnicki_Servis.queue;

import com.example.Korisnicki_Servis.dto.RegistrationMailDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class QueueSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("registration")
    private String queue;

    public void sendMessage(RegistrationMailDTO analysisCreateDto){
        rabbitTemplate.convertAndSend(queue, "message", analysisCreateDto);

        System.out.println("message sent");
    }

}
