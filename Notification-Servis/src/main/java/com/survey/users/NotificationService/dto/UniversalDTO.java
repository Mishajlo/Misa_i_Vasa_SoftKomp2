package com.survey.users.NotificationService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UniversalDTO {
    private String notifType;
    private String recipientEmail;
    private long recipientId;
    private String senderEmail;
    private long senderId;
    private List<String> params;
    private String restaurantName;
    private String restaurantId;
}
