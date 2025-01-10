package com.example.Rezervacije_Servis.dto.reservationDTOs;

import com.example.Rezervacije_Servis.domain.utils.Timeslot;
import com.example.Rezervacije_Servis.domain.utils.User_Data;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationCreationDTO {
    private Boolean reserved = false;
    private int capacity;
    private User_Data user = new User_Data(-1, "");
    private Timeslot timeslot;
}
