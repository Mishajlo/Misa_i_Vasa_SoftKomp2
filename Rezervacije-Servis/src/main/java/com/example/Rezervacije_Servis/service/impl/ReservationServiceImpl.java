package com.example.Rezervacije_Servis.service.impl;

import com.example.Rezervacije_Servis.domain.entities.Reservation;
import com.example.Rezervacije_Servis.domain.entities.Table;
import com.example.Rezervacije_Servis.domain.utils.User_Data;
import com.example.Rezervacije_Servis.dto.reservationDTOs.*;
import com.example.Rezervacije_Servis.repository.ReservationRepository;
import com.example.Rezervacije_Servis.repository.TableRepository;
import com.example.Rezervacije_Servis.service.spec.ReservationService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
//@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ModelMapper modelMapper;
    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository;

    public ReservationServiceImpl(ModelMapper modelMapper, ReservationRepository reservationRepository, TableRepository tableRepository) {
        this.modelMapper = modelMapper;
        this.reservationRepository = reservationRepository;
        this.tableRepository = tableRepository;
        modelMapper.addConverter((mappingContext -> {
            String date = mappingContext.getSource().toString();
            if(date == null || date.isEmpty()) {
                return null;
            }
            return LocalDate.parse(date);
        }));
    }

    @Override
    public long createReservation(long tableId, ReservationCreationDTO reservationCreationDTO) {
        Table table = tableRepository.findById(tableId).orElse(null);
        assert table != null;
        Reservation newReservation = modelMapper.map(reservationCreationDTO, Reservation.class);
        newReservation.setDate(LocalDate.parse(reservationCreationDTO.getDate()));
        newReservation.setStartTime(LocalTime.parse(reservationCreationDTO.getStartTime()));
        newReservation.setEndTime(LocalTime.parse(reservationCreationDTO.getEndTime()));
        List<Reservation> reservationsForTable = reservationRepository.findAllByTable_IdAndDeleteFlagFalse(tableId);

        for (Reservation reservation : reservationsForTable) {
            if (!reservation.getDate().equals(newReservation.getDate())) continue;
            if(( newReservation.getStartTime().isBefore( reservation.getEndTime() ) ) &&
                    ( newReservation.getEndTime().isAfter( reservation.getStartTime() ) ) ){
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
    public long managerCancelReservation(AvailabilityDTO makeAvailable, long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        assert reservation != null;

        if (reservation.getReserved()){
            /** Slanje klijentu maila da je cancelled */
            reservation.setUserData(new User_Data(-1, ""));
            reservation.setReserved(makeAvailable.isMakeAvailable());
            reservationRepository.save(reservation);
            return reservationId;
        }

        reservation.setDeleteFlag(true);
        reservationRepository.save(reservation);

        return reservationId;
    }

    @Override
    public List<ReservationInfoDTO> getAllReservationsByRestaurant(long restaurantId) {
        return reservationRepository.findAllByTable_Restaurant_IdAndDeleteFlagFalse(restaurantId).stream().map(reservation -> modelMapper.map(reservation, ReservationInfoDTO.class)).toList();
    }

    @Override
    public List<ReservationInfoDTO> getReservationsWithFilter(long restaurantId, FilterDTO filterDTO) {
        return reservationRepository.filter(restaurantId, filterDTO).stream().map(reservation -> modelMapper.map(reservation, ReservationInfoDTO.class)).toList();
    }

    @Override
    public List<ReservationInfoDTO> getMyReservations(long userId) {
        return reservationRepository.findAllByUserData_UserIdAndDeleteFlagFalse(userId).stream().map(reservation -> modelMapper.map(reservation, ReservationInfoDTO.class)).toList();
    }

    @Override
    public void cancelAllReservationsForTable(long table_id) {
        List<Reservation> reservationsForTable = reservationRepository.findAllByTable_IdAndDeleteFlagFalse(table_id);

        for (Reservation reservation : reservationsForTable) {
            managerCancelReservation(new AvailabilityDTO(false), reservation.getId());
            reservation.setDeleteFlag(true);
            reservationRepository.save(reservation);
        }

    }


}
