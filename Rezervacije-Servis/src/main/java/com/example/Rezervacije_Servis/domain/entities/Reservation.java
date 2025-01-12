package com.example.Rezervacije_Servis.domain.entities;

import com.example.Rezervacije_Servis.domain.utils.Timeslot;
import com.example.Rezervacije_Servis.domain.utils.User_Data;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@jakarta.persistence.Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "table_id")
    private Table table;
    private Boolean reserved;
    private int capacity;
    @Embedded
    private User_Data userData;
//    @Embedded
//    private Timeslot timeslot;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean reminder = false;

}
