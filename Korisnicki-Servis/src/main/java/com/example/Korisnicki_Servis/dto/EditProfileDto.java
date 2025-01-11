package com.example.Korisnicki_Servis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EditProfileDto {
    private String username;
    private String password;
    private String email;
    private String birthday;
    private String firstName;
    private String lastName;
}
