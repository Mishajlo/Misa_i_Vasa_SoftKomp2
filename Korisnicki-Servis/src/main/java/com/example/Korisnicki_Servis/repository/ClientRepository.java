package com.example.Korisnicki_Servis.repository;

import com.example.Korisnicki_Servis.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    public Client findByEmail(String email);

}
