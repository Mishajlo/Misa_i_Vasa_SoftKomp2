package com.example.Rezervacije_Servis.service.impl;

import com.example.Rezervacije_Servis.domain.entities.Restaurant;
import com.example.Rezervacije_Servis.dto.restaurantDTOs.RestaurantModificationDTO;
import com.example.Rezervacije_Servis.dto.restaurantDTOs.RestaurantDTO;
import com.example.Rezervacije_Servis.repository.RestaurantRepository;
import com.example.Rezervacije_Servis.service.spec.RestaurantService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final ModelMapper modelMapper;
    private RestaurantRepository restaurantRepository;

    @Override
    public Restaurant getRestaurantById(long id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    @Override
    public List<RestaurantDTO> getAllRestaurants() {
//        List<Restaurant> restaurants = restaurantRepository.findAllByDeleteFlagFalse();
//        return List.of(modelMapper.map(restaurants, RestaurantDTO.class));
          return restaurantRepository.findAllByDeleteFlagFalse().stream().map(restaurant -> modelMapper.map(restaurant, RestaurantDTO.class)).toList();
    }

    @Override
    public long modifyRestaurantData(long id, RestaurantModificationDTO restaurantModificationDTO) {
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);

        if (restaurant == null) {
            return -1;
        }

        if (restaurantModificationDTO.getName() != null) {
            restaurant.setName(restaurantModificationDTO.getName());
        }
        if (restaurantModificationDTO.getAddress() != null) {
            restaurant.setAddress(restaurantModificationDTO.getAddress());
        }
        if (restaurantModificationDTO.getDescription() != null) {
            restaurant.setDescription(restaurantModificationDTO.getDescription());
        }
        if (restaurantModificationDTO.getKitchenType() != null) {
            restaurant.setKitchenType(restaurantModificationDTO.getKitchenType());
        }
        if (restaurantModificationDTO.getOpening_hours() != null) {
            restaurant.setOpening_hours(restaurantModificationDTO.getOpening_hours());
        }
        if (restaurantModificationDTO.getClosing_hours() != null) {
            restaurant.setClosing_hours(restaurantModificationDTO.getClosing_hours());
        }

        restaurantRepository.save(restaurant);

        return id;
    }
}
