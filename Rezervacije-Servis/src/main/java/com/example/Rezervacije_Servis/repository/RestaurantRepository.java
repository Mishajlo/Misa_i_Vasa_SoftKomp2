package com.example.Rezervacije_Servis.repository;

import com.example.Rezervacije_Servis.domain.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findById(Long restaurantId);

    List<Restaurant> findAllByDeleteFlagFalse();

}
