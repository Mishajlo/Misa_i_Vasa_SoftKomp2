package com.example.Korisnicki_Servis.service;

import com.example.Korisnicki_Servis.dto.ClientDto;
import com.example.Korisnicki_Servis.dto.RegisterClientDto;

import java.util.List;

public interface ClientService {
    List<ClientDto> findAll();

    ClientDto findById(Long id);

    ClientDto add(RegisterClientDto registerClientDto);

    Boolean remove(Long id);
}
