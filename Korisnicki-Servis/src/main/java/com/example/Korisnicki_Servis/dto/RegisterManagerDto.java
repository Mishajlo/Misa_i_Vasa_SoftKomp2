package com.example.Korisnicki_Servis.dto;

import com.example.Korisnicki_Servis.domain.utils.Restaurant;
import com.example.Korisnicki_Servis.domain.utils.Role;
import com.example.Korisnicki_Servis.domain.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterManagerDto {
    private String username;
    private String password;
    private String email;
    private String birthday;
    private String first_name;
    private String last_name;
    private Restaurant restaurant;
    private String date_of_employment;
    private String status = Status.INACTIVE.toString();
    private String role = Role.MANAGER.toString();
    private String activationCode = String.valueOf(System.currentTimeMillis());

}
