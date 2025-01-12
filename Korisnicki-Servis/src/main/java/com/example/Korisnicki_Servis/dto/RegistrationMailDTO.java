package com.example.Korisnicki_Servis.dto;

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
public class RegistrationMailDTO {
    private String notifType = "activation";
    private String recipientEmail;
    private long recipientId;
    private String senderEmail = "mihajlo2003matic@gmail.com";
    private long senderId = 0;
    private List<String> params = new ArrayList<>();
}
