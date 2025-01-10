package com.example.Korisnicki_Servis.runner;

import com.example.Korisnicki_Servis.domain.entities.Admin;
import com.example.Korisnicki_Servis.domain.entities.Manager;
import com.example.Korisnicki_Servis.domain.utils.Restaurant;
import com.example.Korisnicki_Servis.domain.utils.Role;
import com.example.Korisnicki_Servis.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class AppRunner {

    private UserRepository userRepository;

    @Bean
    CommandLineRunner loadData() {
        return args -> {
            Admin a = new Admin();
            a.setUsername("admin");
            a.setPassword("lozinka");
            a.setRole(Role.ADMIN);
            userRepository.save(a);

            Restaurant r = new Restaurant();
            r.setRestaurant_name("Milky");
            r.setRestaurant_Id("1");
            Manager m = new Manager();
            m.setUsername("manager");
            m.setPassword("<PASSWORD>");
            m.setRole(Role.MANAGER);
            m.setRestaurant(r);
            userRepository.save(m);
        };
    }
}
