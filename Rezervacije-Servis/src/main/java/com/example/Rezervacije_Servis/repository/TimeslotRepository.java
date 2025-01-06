package com.example.Rezervacije_Servis.repository;

import com.example.Rezervacije_Servis.domain.entities.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {
}
