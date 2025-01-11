package com.survey.users.NotificationService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CancellationDTO {
    private String clientEmail;
    private long clientId;
    private String managerEmail;
    private String managerId;
    private String username;
    private LocalDateTime reservationDate;

}
