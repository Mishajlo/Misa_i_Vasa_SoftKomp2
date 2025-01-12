package com.example.Korisnicki_Servis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoggedDto {
    private String token;
    private long userId;
    private String username;
    private String role;
    private String email;
}
