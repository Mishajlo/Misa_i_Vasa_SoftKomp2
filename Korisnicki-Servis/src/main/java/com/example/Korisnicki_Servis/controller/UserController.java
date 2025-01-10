package com.example.Korisnicki_Servis.controller;


import com.example.Korisnicki_Servis.dto.*;
import com.example.Korisnicki_Servis.security.CheckSecurity;
import com.example.Korisnicki_Servis.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(userService.login(loginDto), HttpStatus.OK);
    }

    //@CheckSecurity(roles = {"ADMIN"})
    @GetMapping(value = "/all")
    public ResponseEntity<List<ClientDto>> getAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getClientById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/register/client")
    public ResponseEntity<UserDto> addClient(@RequestBody RegisterClientDto registerClientDto) {
        return new ResponseEntity<>(userService.addClient(registerClientDto), HttpStatus.OK);
    }

    @PostMapping("/register/manager")
    public ResponseEntity<UserDto> addClient(@RequestBody RegisterManagerDto registerManagerDto) {
        return new ResponseEntity<>(userService.addManager(registerManagerDto), HttpStatus.OK);
    }

    //@CheckSecurity(roles = {"ADMIN"})
    @PostMapping("/{id}/delete")
    public ResponseEntity<Boolean> removeClient(@PathVariable Long id) {
        return new ResponseEntity<>(userService.remove(id), HttpStatus.OK);
    }

    //@CheckSecurity(roles = {"ADMIN"})
    @PatchMapping("/restrict/{id}")
    public ResponseEntity<Boolean> restrictUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.restrict(id), HttpStatus.OK);
    }

    //@CheckSecurity(roles = {"ADMIN"})
    @PatchMapping("/allow/{id}")
    public ResponseEntity<Boolean> allowUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.allow(id), HttpStatus.OK);
    }

    @PatchMapping("/logout")
    public ResponseEntity<Boolean> logoutUser() {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Boolean> editProfile(){
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
