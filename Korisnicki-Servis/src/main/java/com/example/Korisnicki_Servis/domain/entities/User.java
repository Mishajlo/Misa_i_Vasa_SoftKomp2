package com.example.Korisnicki_Servis.domain.entities;

import com.example.Korisnicki_Servis.domain.utils.Role;
import com.example.Korisnicki_Servis.domain.utils.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "user_table")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class User extends BaseEntity {
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private LocalDate birthday;
    private String first_name;
    private String last_name;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(unique = true)
    private String activationCode;

}
