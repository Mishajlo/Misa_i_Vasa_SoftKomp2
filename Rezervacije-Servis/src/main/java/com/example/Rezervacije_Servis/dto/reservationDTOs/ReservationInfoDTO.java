package com.example.Rezervacije_Servis.dto.reservationDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationInfoDTO {
    private long reservation_id;
    private Boolean smoking_area;
    private Boolean sitting_area;
    private int capacity;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
