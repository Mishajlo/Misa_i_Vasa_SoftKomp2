package com.example.Rezervacije_Servis.dto.restaurantDTOs;

import com.example.Rezervacije_Servis.domain.utils.Address;
import com.example.Rezervacije_Servis.domain.utils.Kitchen_Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestraurantDTO {
    private long restaurant_id;
    private String name;
    private Address address;
    private String description;
    private String opening_hours;
    private String closing_hours;
    private String kitchenType;
}
