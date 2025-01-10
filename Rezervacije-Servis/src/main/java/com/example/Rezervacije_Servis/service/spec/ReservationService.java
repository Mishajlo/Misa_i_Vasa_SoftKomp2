package com.example.Rezervacije_Servis.service.spec;

import com.example.Rezervacije_Servis.dto.reservationDTOs.*;

import java.util.List;

public interface ReservationService {

    long createReservation(long restaurantId, ReservationCreationDTO reservationCreationDTO);
    long makeReservation(long reservationId, UserInfoDTO userInfoDTO);
    long clientCancelReservation(long clientId, long reservationId);
    long managerCancelReservation(AvailabilityDTO makeAvailable, long reservationId);
    List<ReservationInfoDTO> getAllReservationsByRestaurant(long restaurantId);
    List<ReservationInfoDTO> getReservationsWithFilter(long restaurantId, FilterDTO filterDTO);
    List<ReservationInfoDTO> getMyReservations(long userId);
    void cancelAllReservationsForTable(long table_id);

}
