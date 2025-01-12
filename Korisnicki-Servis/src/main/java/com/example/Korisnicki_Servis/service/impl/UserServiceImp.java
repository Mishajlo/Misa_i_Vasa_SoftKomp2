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

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
//@AllArgsConstructor
public class UserServiceImp implements UserService {
    private UserRepository userRepository;
    private TokenService tokenService;
    private final ModelMapper modelMapper;
    private QueueSender queueSender;
    private ClientRepository clientRepository;

    public UserServiceImp(ModelMapper modelMapper, UserRepository userRepository, TokenService tokenService, QueueSender queueSender, ClientRepository clientRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.queueSender = queueSender;
        this.clientRepository = clientRepository;
        modelMapper.addConverter((mappingContext -> {
            String date = mappingContext.getSource().toString();
            if(date == null || date.isEmpty()) {
                return null;
            }
            return LocalDate.parse(date);
        }));
    }

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
        c.setBirthday(LocalDate.parse(registerClientDto.getBirthday()));
        RegistrationMailDTO registrationMailDTO = new RegistrationMailDTO();
        registrationMailDTO.setRecipientEmail(c.getEmail());
        registrationMailDTO.setRecipientId(c.getId());
        registrationMailDTO.getParams().add(c.getFirst_name());
        registrationMailDTO.getParams().add(c.getLast_name());
        registrationMailDTO.getParams().add(c.getUsername());
        registrationMailDTO.getParams().add("http://localhost:8085/users/users/auth/" + c.getActivationCode());

        queueSender.sendNotification(registrationMailDTO);
        c = userRepository.save(c);
        return modelMapper.map(c, UserDto.class);
    }

    @Override
    public UserDto addManager(RegisterManagerDto registerManagerDto) {
        Manager m = modelMapper.map(registerManagerDto, Manager.class);
        m.setBirthday(LocalDate.parse(registerManagerDto.getBirthday()));
        m.setDate_of_employment(LocalDate.parse(registerManagerDto.getDate_of_employment()));
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
        if(editedProfile.getUsername() != null) {
            user.setUsername(editedProfile.getUsername());
        }
        if(editedProfile.getPassword() != null) {
            String password = user.getPassword();
            user.setPassword(editedProfile.getPassword());
            if (!editedProfile.getPassword().equals(password)) {
                RegistrationMailDTO registrationMailDTO = new RegistrationMailDTO();
                registrationMailDTO.setRecipientEmail(user.getEmail());
                registrationMailDTO.setRecipientId(user.getId());
                registrationMailDTO.getParams().add(user.getUsername());
                registrationMailDTO.getParams().add(user.getPassword());
            }
        }
        if (editedProfile.getEmail() != null) {
            user.setEmail(editedProfile.getEmail());
        }
        if (editedProfile.getBirthday() != null){
            user.setBirthday(LocalDate.parse(editedProfile.getBirthday()));
        }
        if (editedProfile.getFirstName() != null) {
            user.setFirst_name(editedProfile.getFirstName());
        }
        if (editedProfile.getLastName() != null) {
            user.setLast_name(editedProfile.getLastName());
        }
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class) ;
    }

//    @Override
//    public Boolean activateUser(String code) {
//        Client client = clientRepository.findByActivationLink(code);
//        System.out.println(code);
//        client.setStatus(Status.ACTIVE);
//        clientRepository.save(client);
//        return true;
//    }

    @Override
    public Boolean authenticate(String authToken) {
        User u = userRepository.findByActivationCode(authToken).orElse(null);
        if(u != null) {
            if(Objects.equals(u.getActivationCode(), authToken) && u.getStatus() == Status.INACTIVE){
                u.setStatus(Status.ACTIVE);
                userRepository.save(u);
                return true;
            }else{
                return false;
            }
        }
        return null;
    }


    @Override
    public Integer updateReservations(Long id, Boolean increment) {
        Optional<User> client = userRepository.findById(id);
        int reservations = ((Client)client.get()).getReservations();
        if (increment) reservations++;
        else reservations--;
        ((Client)client.get()).setReservations(reservations);
        userRepository.save(client.get());
        return reservations;
    }

    @Override
    public LoggedDto login(LoginDto loginDto) {
        User user = userRepository.findByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword()).orElse(null);
        if(user != null) {
            if(user.getStatus() == Status.BANNED) return new LoggedDto();//"User is banned";
            if(user.getStatus() == Status.INACTIVE) return new LoggedDto();// "User is not activated";
            Claims claims = Jwts.claims();
            claims.put("userId", user.getId());
            claims.put("role", user.getRole().toString());
            claims.put("username", user.getUsername());
            claims.put("email", user.getEmail());
            LoggedDto returndto = new LoggedDto();
            returndto.setUsername(user.getUsername());
            returndto.setUserId(user.getId());
            returndto.setRole(user.getRole().toString());
            returndto.setToken(tokenService.generate(claims));
            returndto.setEmail(user.getEmail());
            return returndto;
        }
        return new LoggedDto();//"User does not exist";
    }





}
