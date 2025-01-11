package com.example.Rezervacije_Servis.domain.utils;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Timeslot {

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

}
