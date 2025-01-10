package com.example.Rezervacije_Servis.service.spec;

import com.example.Rezervacije_Servis.domain.entities.Restaurant;
import com.example.Rezervacije_Servis.dto.restaurantDTOs.RestaurantModificationDTO;
import com.example.Rezervacije_Servis.dto.restaurantDTOs.RestaurantDTO;

import java.util.List;

public interface RestaurantService {

     Restaurant getRestaurantById(long id);
     List<RestaurantDTO> getAllRestaurants();
     long modifyRestaurantData(long id, RestaurantModificationDTO restaurantModificationDTO);

}
