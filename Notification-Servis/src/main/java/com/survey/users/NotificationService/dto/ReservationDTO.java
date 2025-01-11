package com.survey.users.NotificationService.dto;

import java.time.LocalDateTime;

public class ReservationDTO {
    private String clientEmail;
    private long clientId;
    private String managerEmail;
    private String managerId;
    private String username;
    private boolean smokingArea;
    private boolean outsideArea;
    private LocalDateTime reservationDate;
    private String restaurantName;
    private String restaurantAddress;
}
