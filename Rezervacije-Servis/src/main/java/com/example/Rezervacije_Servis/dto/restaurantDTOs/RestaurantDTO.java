package com.example.Rezervacije_Servis.dto.restaurantDTOs;

import com.example.Rezervacije_Servis.domain.utils.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {
    private long id;
    private String name;
    private Address address;
    private String description;
    private String opening_hours;
    private String closing_hours;
    private String kitchenType;
}
