package com.example.Rezervacije_Servis.runner;


import com.example.Rezervacije_Servis.domain.entities.Achievement;
import com.example.Rezervacije_Servis.domain.entities.Reservation;
import com.example.Rezervacije_Servis.domain.entities.Restaurant;
import com.example.Rezervacije_Servis.domain.entities.Table;
import com.example.Rezervacije_Servis.domain.utils.Address;
import com.example.Rezervacije_Servis.domain.utils.Kitchen_Type;
import com.example.Rezervacije_Servis.domain.utils.Timeslot;
import com.example.Rezervacije_Servis.repository.AchievementRepository;
import com.example.Rezervacije_Servis.repository.ReservationRepository;
import com.example.Rezervacije_Servis.repository.RestaurantRepository;
import com.example.Rezervacije_Servis.repository.TableRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Configuration
@AllArgsConstructor
public class AppRunner {

    private RestaurantRepository restaurantRepository;
    private AchievementRepository achievementRepository;
    private TableRepository tableRepository;
    private ReservationRepository reservationRepository;

    @Bean
    CommandLineRunner loadData() {
        return args -> {


            Restaurant r = new Restaurant();
            r.setName("Milky");
            r.setAddress(new Address("daqsda","dasda","dadadaw","dawdad"));
            r.setTables(5);
            r.setManagerEmail("mihajlo2003matic@gmail.com");
            r.setManagerId(2);
            r.setDescription("doqjdoajidw");
            r.setClosing_hours(LocalTime.parse("12:00:00"));
            r.setKitchenType(Kitchen_Type.Indian);
            restaurantRepository.save(r);
            Achievement a = new Achievement();
            a.setRestaurant(r);
            a.setTitle("Best restaurant");
            a.setDescription("test");
            achievementRepository.save(a);

            Table sto =  new Table();
            sto.setRestaurant(r);
            sto.setSitting_area(false);
            sto.setSmoking_area(true);
            tableRepository.save(sto);

            Achievement achievement = new Achievement();
            achievement.setRestaurant(r);
            achievement.setDescription("Super Kul Probni Achievement");
            achievement.setTitle("Probica :D");
            achievement.setCondition(1);
            achievementRepository.save(achievement);

            Reservation proba = new Reservation();
            proba.setTable(sto);
            proba.setCapacity(3);
            proba.setReserved(false);
            Date datum = new Date(125, Calendar.JANUARY, 13);
            proba.setDate(LocalDate.parse("2025-01-12"));
            proba.setStartTime(LocalTime.parse("15:00:00"));
            proba.setEndTime(LocalTime.parse("16:00:00"));
            reservationRepository.save(proba);
        };
    }
}
