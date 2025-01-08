package com.example.Korisnicki_Servis.service.impl;

import com.example.Korisnicki_Servis.domain.entities.Client;
import com.example.Korisnicki_Servis.dto.ClientDto;
import com.example.Korisnicki_Servis.dto.RegisterClientDto;
import com.example.Korisnicki_Servis.repository.ClientRepository;
import com.example.Korisnicki_Servis.service.ClientService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream().map(client -> modelMapper.map(client, ClientDto.class)).toList();
    }

    @Override
    public ClientDto add(RegisterClientDto registerClientDto) {
        Client c = modelMapper.map(registerClientDto, Client.class);
        c = clientRepository.save(c);
        return modelMapper.map(c, ClientDto.class);
    }

    @Override
    public Boolean remove(Long id) {
        return null;
    }
}
