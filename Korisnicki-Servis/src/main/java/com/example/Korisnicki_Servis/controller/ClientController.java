package com.example.Korisnicki_Servis.controller;


import com.example.Korisnicki_Servis.dto.ClientDto;
import com.example.Korisnicki_Servis.dto.RegisterClientDto;
import com.example.Korisnicki_Servis.security.CheckSecurity;
import com.example.Korisnicki_Servis.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {
    private ClientService clientService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<ClientDto>> getAll() {
        return new ResponseEntity<>(clientService.findAll(), HttpStatus.OK);
    }
    
    @GetMapping(value = "/{id}") 
    public ResponseEntity<ClientDto> getClientById(@PathVariable Long id) {
        return new ResponseEntity<>(clientService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClientDto> addClient(@RequestBody RegisterClientDto registerClientDto) {
        return new ResponseEntity<>(clientService.add(registerClientDto), HttpStatus.OK);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<Boolean> removeClient(@PathVariable Long id) {
        return new ResponseEntity<>(clientService.remove(id), HttpStatus.OK);
    }



}
