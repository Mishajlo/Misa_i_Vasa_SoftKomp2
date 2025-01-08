package com.example.Korisnicki_Servis.service.impl;

import com.example.Korisnicki_Servis.domain.entities.User;
import com.example.Korisnicki_Servis.dto.LoginDto;
import com.example.Korisnicki_Servis.repository.UserRepository;
import com.example.Korisnicki_Servis.security.service.TokenService;
import com.example.Korisnicki_Servis.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImp implements UserService {
    private UserRepository userRepository;
    private TokenService tokenService;


    @Override
    public String login(LoginDto loginDto) {
        User user = userRepository.findByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword()).orElse(null);
        if(user != null) {
            Claims claims = Jwts.claims();
            claims.put("username", user.getRole());
            claims.put("password", user.getPassword());
            return tokenService.generate(claims);
        }
        return "User does not exist";
    }
}
