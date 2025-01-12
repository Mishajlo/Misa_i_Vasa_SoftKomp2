package com.example.Rezervacije_Servis.dto.QueueDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotifServiceDTO {
    private String notifType;
    private String recipientEmail;
    private long recipientId;
    private List<String> params = new ArrayList<>();
    private String restaurantName;
    private long restaurantId;
}
