package com.survey.users.NotificationService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {
    private String recipientEmail;
    private long recipientId;
    private String senderEmail;
    private long senderId;
    private String username;
    private String firstName;
    private String lastName;
    private String activationLink;
}
