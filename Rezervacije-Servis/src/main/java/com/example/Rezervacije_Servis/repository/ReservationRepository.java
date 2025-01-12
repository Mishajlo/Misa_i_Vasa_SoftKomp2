package com.example.Rezervacije_Servis.repository;

import com.example.Rezervacije_Servis.domain.entities.Reservation;
import com.example.Rezervacije_Servis.dto.reservationDTOs.FilterDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByUserData_UserIdAndDeleteFlagFalse(long id);

    List<Reservation> findAllByTable_Restaurant_IdAndDeleteFlagFalse(long id);

    List<Reservation> findAllByDeleteFlagFalse();

    @Query("select r from Reservation r where " +
            "r.table.restaurant.id = :#{#restaurantId} and " +
            "r.deleteFlag = false and " +
            "r.reserved = false and " +
            "r.capacity = :#{#filter.capacity} and " +
            "r.table.sitting_area = :#{#filter.outside} and " +
            "r.table.smoking_area = :#{#filter.smoking} and " +
            "r.timeslot = :#{#filter.timeslot} and " +
            "r.table.restaurant.address = :#{#filter.address} and " +
            "r.table.restaurant.kitchenType = :#{#filter.kitchenType}")
    List<Reservation> filter(@Param("restaurantId") long restaurantId, @Param("filter") FilterDTO filter);

    List<Reservation> findAllByTable_IdAndDeleteFlagFalse(long table_id);


    @Query("select r from Reservation r where r.reserved = true and r.reminder = false")
    List<Reservation> reminderQuery();


}
