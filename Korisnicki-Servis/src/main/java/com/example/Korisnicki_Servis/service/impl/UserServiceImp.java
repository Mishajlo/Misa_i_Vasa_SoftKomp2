package com.example.Korisnicki_Servis.service.impl;

import com.example.Korisnicki_Servis.domain.entities.Client;
import com.example.Korisnicki_Servis.domain.entities.Manager;
import com.example.Korisnicki_Servis.domain.entities.User;
import com.example.Korisnicki_Servis.domain.utils.Status;
import com.example.Korisnicki_Servis.dto.*;
import com.example.Korisnicki_Servis.queue.QueueSender;
import com.example.Korisnicki_Servis.repository.ClientRepository;
import com.example.Korisnicki_Servis.repository.UserRepository;
import com.example.Korisnicki_Servis.security.service.TokenService;
import com.example.Korisnicki_Servis.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImp implements UserService {
    private UserRepository userRepository;
    private TokenService tokenService;
    private final ModelMapper modelMapper;
    private QueueSender queueSender;
    private ClientRepository clientRepository;

    @Override
    public List<ClientDto> findAll() {
        return userRepository.findAll().stream().map(user -> modelMapper.map(user, ClientDto.class)).toList();
    }

    @Override
    public UserDto findById(Long id) {
        User u = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " does not exist"));
        return modelMapper.map(u, UserDto.class);
    }


    @Override
    public UserDto addClient(RegisterClientDto registerClientDto) {
        Client c = modelMapper.map(registerClientDto, Client.class);
        c.setActivationLink(registerClientDto.getUsername());
        RegistrationMailDTO registrationMailDTO = new RegistrationMailDTO();
        registrationMailDTO.setRecipientEmail(c.getEmail());
        registrationMailDTO.setRecipientId(c.getId());
        registrationMailDTO.setUsername(c.getUsername());
        registrationMailDTO.setFirstName(c.getFirst_name());
        registrationMailDTO.setLastName(c.getLast_name());
        registrationMailDTO.setActivationLink("http://localhost:8080/users/activate" + c.getActivationLink());
        queueSender.sendMessage(registrationMailDTO);
        c = userRepository.save(c);
        return modelMapper.map(c, UserDto.class);
    }

    @Override
    public UserDto addManager(RegisterManagerDto registerManagerDto) {
        Manager m = modelMapper.map(registerManagerDto, Manager.class);
        m = userRepository.save(m);
        return modelMapper.map(m, UserDto.class);
    }

    @Override
    public Boolean restrict(Long id) {
        User u = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " does not exist"));
        u.setStatus(Status.BANNED);
        userRepository.save(u);
        return true;
    }

    @Override
    public Boolean allow(Long id) {
        User u = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " does not exist"));
        u.setStatus(Status.ACTIVE);
        userRepository.save(u);
        return true;
    }

    @Override
    public Boolean remove(Long id) {
        User u = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " does not exist"));
        u.setDelete_flag(true);
        userRepository.save(u);
        return true;
    }

    @Override
    public UserDto editProfile(Long id,EditProfileDto editedProfile) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " does not exist"));
        user.setUsername(editedProfile.getUsername());
        user.setPassword(editedProfile.getPassword());
        user.setEmail(editedProfile.getEmail());
        user.setBirthday(editedProfile.getBirthday());
        user.setFirst_name(editedProfile.getFirstName());
        user.setLast_name(editedProfile.getLastName());
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class) ;
    }

    @Override
    public Boolean activateUser(String code) {
        Client client = clientRepository.findByActivationLink(code);
        client.setStatus(Status.ACTIVE);
        clientRepository.save(client);
        return true;
    }

    @Override
    public String login(LoginDto loginDto) {
        User user = userRepository.findByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword()).orElse(null);
        if(user != null) {
            Claims claims = Jwts.claims();
            claims.put("role", user.getRole().toString());
            claims.put("username", user.getUsername());
            return tokenService.generate(claims);
        }
        return "User does not exist";
    }




}
