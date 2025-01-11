package com.example.Korisnicki_Servis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationMailDTO {
    private String recipientEmail;
    private long recipientId;
    private String senderEmail = "mihajlo2003matic@gmail.com";
    private long senderId = 0;
    private String username;
    private String firstName;
    private String lastName;
    private String activationLink;
}
