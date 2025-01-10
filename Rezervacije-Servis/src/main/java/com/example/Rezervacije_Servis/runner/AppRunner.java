package com.example.Rezervacije_Servis.runner;


import com.example.Rezervacije_Servis.domain.entities.Restaurant;
import com.example.Rezervacije_Servis.domain.utils.Address;
import com.example.Rezervacije_Servis.domain.utils.Kitchen_Type;
import com.example.Rezervacije_Servis.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class AppRunner {

    private RestaurantRepository restaurantRepository;

    @Bean
    CommandLineRunner loadData() {
        return args -> {


            Restaurant r = new Restaurant();
            r.setName("Milky");
            r.setAddress(new Address("daqsda","dasda","dadadaw","dawdad"));
            r.setTables(5);
            r.setDescription("doqjdoajidw");
            r.setClosing_hours("12");
            r.setKitchenType(Kitchen_Type.Indian);

            restaurantRepository.save(r);

        };
    }
}
