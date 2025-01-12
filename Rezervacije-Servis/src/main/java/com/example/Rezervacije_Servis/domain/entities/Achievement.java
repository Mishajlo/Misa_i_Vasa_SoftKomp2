package com.example.Rezervacije_Servis.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Loyalty")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Achievement extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    private int condition;
    private String title;
    private String description;

}
