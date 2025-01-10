package com.example.Rezervacije_Servis.repository;

import com.example.Rezervacije_Servis.domain.entities.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {

    Optional<Table> findById(long id);

    List<Table> findAllByDeleteFlagFalseAndRestaurant_Id(Long restaurantId);

}
