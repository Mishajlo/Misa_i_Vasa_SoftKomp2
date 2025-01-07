package com.example.Korisnicki_Servis.service.impl;

import com.example.Korisnicki_Servis.dto.LoginDto;
import com.example.Korisnicki_Servis.repository.ClientRepository;
import com.example.Korisnicki_Servis.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class ClientServiceImp implements UserService {
    private ClientRepository clientRepository;


    @Override
    public String login(LoginDto loginDto) {
        return "";
    }
}
