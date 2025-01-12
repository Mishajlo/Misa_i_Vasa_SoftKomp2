package com.example.Korisnicki_Servis.service;

import com.example.Korisnicki_Servis.dto.*;

import java.util.List;

public interface UserService {

    LoggedDto login(LoginDto loginDto);

    List<ClientDto> findAll();

    UserDto findById(Long id);

    UserDto addClient(RegisterClientDto registerClientDto);

    UserDto addManager(RegisterManagerDto registerManagerDto);

    Boolean restrict(Long id);

    Boolean allow(Long id);

    Boolean remove(Long id);

    UserDto editProfile(Long id,EditProfileDto editProfileDto);

    //Boolean activateUser(String code);

    Boolean authenticate(String authToken);

    Integer updateReservations(Long id, Boolean increment);

}
