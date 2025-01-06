package com.example.Korisnicki_Servis.domain.entities;

import com.example.Korisnicki_Servis.domain.utils.Restaurant;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;

import java.util.Date;

@Entity
@DiscriminatorValue("MANAGER")
public class Manager extends User {

    @Embedded
    private Restaurant restaurant;
    private Date date_of_employment;

}
