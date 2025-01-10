package com.example.Rezervacije_Servis.controller;

import com.example.Rezervacije_Servis.dto.reservationDTOs.FilterDTO;
import com.example.Rezervacije_Servis.dto.reservationDTOs.ReservationCreationDTO;
import com.example.Rezervacije_Servis.dto.reservationDTOs.ReservationInfoDTO;
import com.example.Rezervacije_Servis.dto.reservationDTOs.UserInfoDTO;
import com.example.Rezervacije_Servis.security.CheckSecurity;
import com.example.Rezervacije_Servis.service.spec.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@AllArgsConstructor
public class ReservationController {

    private ReservationService reservationService;

    @CheckSecurity(roles = {"MANAGER"})
    @PostMapping(value = "/mng/{table_id}")
    public ResponseEntity<Long> createReservation(@PathVariable long table_id, @RequestBody ReservationCreationDTO reservationCreationDTO) {
        return new ResponseEntity<>(reservationService.createReservation(table_id, reservationCreationDTO), HttpStatus.CREATED);
    }

    @CheckSecurity(roles = {"MANAGER"})
    @DeleteMapping(value = "/mng/{reservation_id}")
    public ResponseEntity<Long> managerCancelReservation(@PathVariable long reservation_id, @RequestBody boolean makeAvailable) {
        return new ResponseEntity<>(reservationService.managerCancelReservation(makeAvailable, reservation_id), HttpStatus.OK);
    }

    @CheckSecurity(roles = {"MANAGER"})
    @GetMapping(value = "/mng/{restaurant_id}")
    public ResponseEntity<List<ReservationInfoDTO>> getAllReservations(@PathVariable long restaurant_id) {
        return new ResponseEntity<>(reservationService.getAllReservationsByRestaurant(restaurant_id), HttpStatus.OK);
    }

    @CheckSecurity(roles = {"CLIENT"})
    @PostMapping(value = "/clt/{reservation_id}")
    public ResponseEntity<Long> bookReservation(@PathVariable long reservation_id, @RequestBody UserInfoDTO userInfoDTO) {
        return new ResponseEntity<>(reservationService.makeReservation(reservation_id, userInfoDTO), HttpStatus.OK);
    }

    @CheckSecurity(roles = {"CLIENT"})
    @DeleteMapping(value = "/clt/{reservation_id}&{user_id}")
    public ResponseEntity<Long> clientCancelReservation(@PathVariable long reservation_id, @PathVariable long user_id) {
        return new ResponseEntity<>(reservationService.clientCancelReservation(user_id, reservation_id), HttpStatus.OK);
    }

    @CheckSecurity(roles = {"CLIENT"})
    @GetMapping(value = "/clt/{user_id}")
    public ResponseEntity<List<ReservationInfoDTO>> getClientReservations(@PathVariable long user_id) {
        return new ResponseEntity<>(reservationService.getMyReservations(user_id), HttpStatus.OK);
    }

    @GetMapping(value = "/{restaurant_id}")
    public ResponseEntity<List<ReservationInfoDTO>> getAllReservationsFiltered(@PathVariable long restaurant_id, @RequestBody FilterDTO filterDTO) {
        return new ResponseEntity<>(reservationService.getReservationsWithFilter(restaurant_id, filterDTO), HttpStatus.OK);
    }

}
