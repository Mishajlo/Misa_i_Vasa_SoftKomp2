package com.example.Korisnicki_Servis.domain.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("CLIENT")
public class Client extends User {

    private int reservations;

}
