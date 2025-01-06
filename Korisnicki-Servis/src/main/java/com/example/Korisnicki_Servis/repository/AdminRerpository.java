package com.example.Korisnicki_Servis.repository;

import com.example.Korisnicki_Servis.domain.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRerpository extends JpaRepository<Admin, Long> {
}
