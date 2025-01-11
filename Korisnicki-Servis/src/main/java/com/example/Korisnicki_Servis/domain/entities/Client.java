package com.example.Korisnicki_Servis.domain.entities;


import com.example.Korisnicki_Servis.domain.utils.StringRandom;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.processing.Exclude;

@Getter
@Setter
@Entity
@DiscriminatorValue("CLIENT")
public class Client extends User {
    @Transient
    private StringRandom stringRandom = new StringRandom();

    private int reservations;
    private String activation_code = stringRandom.generateRandomString();

}
