package com.example.Korisnicki_Servis.dto;

import com.example.Korisnicki_Servis.domain.utils.Role;
import com.example.Korisnicki_Servis.domain.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterClientDto {
    private String username;
    private String password;
    private String email;
    private Date birthday;
    private String first_name;
    private String last_name;
    private String status = Status.INACTIVE.toString();
    private String role = Role.ClIENT.toString();
    private int reservations = 0;
}
