package com.survey.users.NotificationService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeDTO {
    private String recipientEmail;
    private long recipientId;
    private String senderEmail;
    private String senderId;
    private String username;
    private String oldPassword;
    private String newPassword;
}
