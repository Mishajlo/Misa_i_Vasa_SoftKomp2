package com.example.Korisnicki_Servis.repository;

import com.example.Korisnicki_Servis.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    Client findByActivationLink(String link);

}
