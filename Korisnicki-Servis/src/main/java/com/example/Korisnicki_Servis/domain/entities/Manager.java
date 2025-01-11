package com.example.Korisnicki_Servis.domain.entities;

import com.example.Korisnicki_Servis.domain.utils.Restaurant;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@DiscriminatorValue("MANAGER")
public class Manager extends User {

    @Embedded
    private Restaurant restaurant;
    private LocalDate date_of_employment;

}
