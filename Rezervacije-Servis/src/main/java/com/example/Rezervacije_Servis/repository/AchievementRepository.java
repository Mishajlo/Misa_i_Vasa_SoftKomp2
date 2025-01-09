package com.example.Rezervacije_Servis.repository;

import com.example.Rezervacije_Servis.domain.entities.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
}
