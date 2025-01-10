package com.example.Rezervacije_Servis.controller;

import com.example.Rezervacije_Servis.security.CheckSecurity;
import com.example.Rezervacije_Servis.service.spec.RestaurantService;
import com.example.Rezervacije_Servis.dto.restaurantDTOs.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@AllArgsConstructor
public class RestaurantController {

    private RestaurantService restaurantService;

    @GetMapping()
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @CheckSecurity(roles = {"MANAGER"})
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Long> getRestaurantById(@RequestHeader("Authorization") String authorization, @PathVariable long id, @RequestBody RestaurantModificationDTO modificationDTO) {
        return ResponseEntity.ok(restaurantService.modifyRestaurantData(id, modificationDTO));
    }

}
