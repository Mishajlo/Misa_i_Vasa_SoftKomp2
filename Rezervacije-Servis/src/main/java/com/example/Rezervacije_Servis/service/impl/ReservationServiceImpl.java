package com.example.Rezervacije_Servis.service.impl;

import com.example.Rezervacije_Servis.domain.entities.Reservation;
import com.example.Rezervacije_Servis.domain.entities.Restaurant;
import com.example.Rezervacije_Servis.domain.entities.Table;
import com.example.Rezervacije_Servis.domain.utils.User_Data;
import com.example.Rezervacije_Servis.dto.reservationDTOs.FilterDTO;
import com.example.Rezervacije_Servis.dto.reservationDTOs.ReservationCreationDTO;
import com.example.Rezervacije_Servis.dto.reservationDTOs.ReservationInfoDTO;
import com.example.Rezervacije_Servis.dto.reservationDTOs.UserInfoDTO;
import com.example.Rezervacije_Servis.repository.ReservationRepository;
import com.example.Rezervacije_Servis.repository.RestaurantRepository;
import com.example.Rezervacije_Servis.repository.TableRepository;
import com.example.Rezervacije_Servis.service.spec.ReservationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ModelMapper modelMapper;
    private ReservationRepository reservationRepository;
    private TableRepository tableRepository;

    @Override
    public long createReservation(long tableId, ReservationCreationDTO reservationCreationDTO) {
        Table table = tableRepository.findById(tableId).orElse(null);
        assert table != null;
        Reservation newReservation = modelMapper.map(reservationCreationDTO, Reservation.class);

        List<Reservation> reservationsForTable = reservationRepository.findAllByTable_IdAndDeleteFlagFalse(tableId);

        for (Reservation reservation : reservationsForTable) {
            if (!reservation.getTimeslot().getDate().equals(newReservation.getTimeslot().getDate())) continue;
            if(( newReservation.getTimeslot().getStartTime().isBefore( reservation.getTimeslot().getEndTime() ) ) &&
                    ( newReservation.getTimeslot().getEndTime().isAfter( reservation.getTimeslot().getStartTime() ) ) ){
                return -1;
            }
        }

        newReservation.setTable(table);
        Reservation savedReservation = reservationRepository.save(newReservation);
        return savedReservation.getId();
    }

    @Override
    public long makeReservation(long reservationId, UserInfoDTO userInfoDTO) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        assert reservation != null;
        if (reservation.getReserved()){
            return -1;
        }
        /** Logika za slanje mejla potvrde i porveru broja rezervacija */
        reservation.setUserData(modelMapper.map(userInfoDTO, User_Data.class));
        reservation.setReserved(true);
        reservationRepository.save(reservation);
        return reservationId;
    }

    @Override
    public long clientCancelReservation(long clientId, long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        assert reservation != null;
        if (reservation.getUserData().getUserId() != clientId){
            return -1;
        }
        /** Logika za mail otkazivanja */
        reservation.setUserData(new User_Data(-1, ""));
        reservation.setReserved(false);
        return reservationId;
    }

    @Override
    public long managerCancelReservation(boolean makeAvailable, long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        assert reservation != null;

        if (reservation.getReserved()){
            /** Slanje klijentu maila da je cancelled */
            reservation.setUserData(new User_Data(-1, ""));
            reservation.setReserved(makeAvailable);
            reservationRepository.save(reservation);
            return reservationId;
        }

        reservation.setDeleteFlag(true);
        reservationRepository.save(reservation);

        return reservationId;
    }

    @Override
    public List<ReservationInfoDTO> getAllReservationsByRestaurant(long restaurantId) {
        List<Reservation> reservations = reservationRepository.findAllByTable_Restaurant_IdAndDeleteFlagFalse(restaurantId);
        return List.of(modelMapper.map(reservations, ReservationInfoDTO.class));
    }

    @Override
    public List<ReservationInfoDTO> getReservationsWithFilter(long restaurantId, FilterDTO filterDTO) {
        List<Reservation> reservations = reservationRepository.filter(restaurantId, filterDTO);
        return List.of(modelMapper.map(reservations, ReservationInfoDTO.class));
    }

    @Override
    public List<ReservationInfoDTO> getMyReservations(long userId) {
        List<Reservation> reservations = reservationRepository.findAllByUserData_UserIdAndDeleteFlagFalse(userId);
        return List.of(modelMapper.map(reservations, ReservationInfoDTO.class));
    }

    @Override
    public void cancelAllReservationsForTable(long table_id) {
        List<Reservation> reservationsForTable = reservationRepository.findAllByTable_IdAndDeleteFlagFalse(table_id);

        for (Reservation reservation : reservationsForTable) {
            managerCancelReservation(false, reservation.getId());
            reservation.setDeleteFlag(true);
            reservationRepository.save(reservation);
        }

    }


}
