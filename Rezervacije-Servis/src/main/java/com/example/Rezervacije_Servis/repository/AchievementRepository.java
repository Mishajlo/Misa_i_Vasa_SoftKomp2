package com.example.Rezervacije_Servis.repository;

import com.example.Rezervacije_Servis.domain.entities.Achievement;
import com.example.Rezervacije_Servis.domain.entities.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    List<Achievement> findAllByDeleteFlagFalseAndRestaurant_Id(Long restaurantId);
}
