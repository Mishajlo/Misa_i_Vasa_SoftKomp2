package com.example.Rezervacije_Servis.domain.entities;

import com.example.Rezervacije_Servis.domain.utils.Address;
import com.example.Rezervacije_Servis.domain.utils.Kitchen_Type;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant extends BaseEntity {

    private String name;
    @Embedded
    private Address address;
    private String description;
    private int tables;
    private String opening_hours;
    private String closing_hours;
    @Enumerated(EnumType.STRING)
    private Kitchen_Type kitchenType;
    private long managerId;
    private String managerEmail;

}
