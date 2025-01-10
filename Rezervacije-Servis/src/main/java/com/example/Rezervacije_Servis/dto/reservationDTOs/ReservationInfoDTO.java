package com.example.Rezervacije_Servis.dto.reservationDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationInfoDTO {
    private long reservation_id;
    private Boolean smoking_area;
    private Boolean sitting_area;
    private int capacity;
    private Date date;
    private LocalTime start_time;
    private LocalTime end_time;
}
