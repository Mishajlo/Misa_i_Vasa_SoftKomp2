package com.example.Rezervacije_Servis.domain.entities;

import com.example.Rezervacije_Servis.domain.utils.User_Data;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@jakarta.persistence.Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "timeslot_id")
    private Timeslot timeslot;
    @ManyToOne
    @JoinColumn(name = "table_id")
    private Table table;
    private Boolean reserved;
    private int capacity;
    @Embedded
    private User_Data user_data;

}
