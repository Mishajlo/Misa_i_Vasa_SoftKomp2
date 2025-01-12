package com.example.Rezervacije_Servis.dto.reservationDTOs;

import com.example.Rezervacije_Servis.domain.utils.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterDTO {
    private String kitchenType;
    private Address address;
    private Boolean smoking;
    private Boolean outside;
    private Integer capacity;
    //private Timeslot timeslot;
    private String date;
    private String startTime;
    private String endTime;
}
