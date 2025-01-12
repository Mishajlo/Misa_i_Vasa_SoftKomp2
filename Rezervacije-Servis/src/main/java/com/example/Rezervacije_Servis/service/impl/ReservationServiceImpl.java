package com.example.Rezervacije_Servis.service.impl;

import com.example.Rezervacije_Servis.domain.entities.Reservation;
import com.example.Rezervacije_Servis.domain.entities.Restaurant;
import com.example.Rezervacije_Servis.domain.entities.Table;
import com.example.Rezervacije_Servis.domain.utils.User_Data;
import com.example.Rezervacije_Servis.dto.QueueDTOs.NotifServiceDTO;
import com.example.Rezervacije_Servis.dto.reservationDTOs.*;
import com.example.Rezervacije_Servis.queues.SendToClient;
import com.example.Rezervacije_Servis.queues.SendToNotification;
import com.example.Rezervacije_Servis.repository.ReservationRepository;
import com.example.Rezervacije_Servis.repository.RestaurantRepository;
import com.example.Rezervacije_Servis.repository.TableRepository;
import com.example.Rezervacije_Servis.service.spec.AchievementService;
import com.example.Rezervacije_Servis.service.spec.ReservationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
    private ReservationRepository reservationRepository;
    private TableRepository tableRepository;
    private RestaurantRepository restaurantRepository;
    private SendToClient sendToClient;
    private SendToNotification sendToNotification;
    private AchievementService achievementService;

    public ReservationServiceImpl(ModelMapper modelMapper, ReservationRepository reservationRepository, TableRepository tableRepository, RestaurantRepository restaurantRepository, SendToClient sendToClient, SendToNotification sendToNotification, AchievementService achievementService) {
        this.modelMapper = modelMapper;
        this.reservationRepository = reservationRepository;
        this.tableRepository = tableRepository;
        this.restaurantRepository = restaurantRepository;
        this.sendToClient = sendToClient;
        this.sendToNotification = sendToNotification;
        this.achievementService = achievementService;
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
        Restaurant restaurant = restaurantRepository.findById(table.getRestaurant().getId()).orElse(null);
        assert restaurant != null;
        Reservation newReservation = modelMapper.map(reservationCreationDTO, Reservation.class);
        newReservation.setDate(LocalDate.parse(reservationCreationDTO.getDate()));
        newReservation.setStartTime(LocalTime.parse(reservationCreationDTO.getStartTime()));
        newReservation.setEndTime(LocalTime.parse(reservationCreationDTO.getEndTime()));
        if (LocalTime.parse(reservationCreationDTO.getStartTime()).isAfter(restaurant.getClosing_hours())){
            return -1;
        }
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
    public long makeReservation(long reservationId, UserInfoDTO userInfoDTO, String token) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        assert reservation != null;
        if (reservation.getReserved()){
            return -1;
        }
        int resCount = sendToClient.updateReservationCount(userInfoDTO.getUserId(), true, token);

        NotifServiceDTO reservationNotification = new NotifServiceDTO();
        reservationNotification.setNotifType("reservation");
        reservationNotification.setRecipientEmail(userInfoDTO.getEmail());
        reservationNotification.setRecipientId(userInfoDTO.getUserId());

        Restaurant restaurant = restaurantRepository.findById(reservation.getTable().getRestaurant().getId()).orElse(null);

        assert restaurant != null;
        achievementService.getByResCount(resCount, userInfoDTO, restaurant);
        reservationNotification.setRestaurantName(restaurant.getName());
        reservationNotification.setRestaurantId(restaurant.getId());
        reservationNotification.getParams().add(restaurant.getName());
        reservationNotification.getParams().add(String.valueOf(reservation.getTable().getId()));
        reservationNotification.getParams().add(reservation.getDate().toString());
        sendToNotification.sendNotification(reservationNotification);

        reservation.setUserData(modelMapper.map(userInfoDTO, User_Data.class));
        reservation.setReserved(true);
        reservationRepository.save(reservation);
        return reservationId;
    }

    @Override
    public long clientCancelReservation(long clientId, long reservationId, String token) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        assert reservation != null;
        if (reservation.getUserData().getUserId() != clientId){
            return -1;
        }

        NotifServiceDTO reservationNotification = new NotifServiceDTO();
        reservationNotification.setNotifType("client_cancel_reservation");
        sendToClient.updateReservationCount(reservation.getUserData().getUserId(), false, token);
        Restaurant restaurant = restaurantRepository.findById(reservation.getTable().getRestaurant().getId()).orElse(null);

        assert restaurant != null;
        reservationNotification.setRecipientEmail(restaurant.getManagerEmail());
        reservationNotification.setRecipientId(restaurant.getManagerId());
        reservationNotification.setRestaurantName(restaurant.getName());
        reservationNotification.setRestaurantId(restaurant.getId());
        reservationNotification.getParams().add(reservation.getUserData().getEmail());
        reservationNotification.getParams().add(reservation.getDate().toString());
        reservationNotification.getParams().add(restaurant.getName());
        sendToNotification.sendNotification(reservationNotification);

        reservation.setUserData(new User_Data(-1, ""));
        reservation.setReserved(false);
        return reservationId;
    }

    @Override
    public long managerCancelReservation(AvailabilityDTO makeAvailable, long reservationId, String token) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        assert reservation != null;

        if (reservation.getReserved() && reservation.getUserData().getUserId() != -1){

            NotifServiceDTO reservationNotification = new NotifServiceDTO();
            reservationNotification.setNotifType("manager_cancel_reservation");
            sendToClient.updateReservationCount(reservation.getUserData().getUserId(), false, token);
            Restaurant restaurant = restaurantRepository.findById(reservation.getTable().getRestaurant().getId()).orElse(null);

            assert restaurant != null;
            reservationNotification.setRecipientEmail(reservation.getUserData().getEmail());
            reservationNotification.setRecipientId(reservation.getUserData().getUserId());
            reservationNotification.setRestaurantName(restaurant.getName());
            reservationNotification.setRestaurantId(restaurant.getId());
            reservationNotification.getParams().add(restaurant.getName());
            reservationNotification.getParams().add(reservation.getDate().toString());
            sendToNotification.sendNotification(reservationNotification);

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
        List<Reservation> toCheck = reservationRepository.filter(restaurantId);

        for (int i =0; i<toCheck.size(); i++){
            Reservation check = toCheck.get(i);
            System.out.println(check);
            if (filterDTO.getCapacity() != null && filterDTO.getCapacity() != check.getCapacity()) {
                System.out.println(1);
                toCheck.remove(i);
                i--;
                continue;
            }
            if (filterDTO.getOutside() != null && filterDTO.getOutside() != check.getTable().getSitting_area()){
                System.out.println(2);
                toCheck.remove(i);
                i--;
                continue;
            }
            if (filterDTO.getSmoking() != null && filterDTO.getSmoking() != check.getTable().getSmoking_area()){
                System.out.println(3);
                toCheck.remove(i);
                i--;
                continue;
            }
            if (filterDTO.getAddress() != null && filterDTO.getAddress() != check.getTable().getRestaurant().getAddress()){
                System.out.println(4);
                toCheck.remove(i);
                i--;
                continue;
            }
            if (filterDTO.getKitchenType() != null && !filterDTO.getKitchenType().equals(check.getTable().getRestaurant().getKitchenType().toString())){
                System.out.println(5);
                toCheck.remove(i);
                i--;
                continue;
            }
            if (filterDTO.getDate() != null && !filterDTO.getDate().equals(check.getDate().toString())){
                System.out.println(6);
                toCheck.remove(i);
                i--;
                continue;
            }
        }

        return toCheck.stream().map(reservation -> modelMapper.map(reservation, ReservationInfoDTO.class)).toList();
    }

    @Override
    public List<ReservationInfoDTO> getMyReservations(long userId) {
        return reservationRepository.findAllByUserData_UserIdAndDeleteFlagFalse(userId).stream().map(reservation -> modelMapper.map(reservation, ReservationInfoDTO.class)).toList();
    }

    @Override
    public void cancelAllReservationsForTable(long table_id, String token) {
        List<Reservation> reservationsForTable = reservationRepository.findAllByTable_IdAndDeleteFlagFalse(table_id);

        for (Reservation reservation : reservationsForTable) {
            managerCancelReservation(new AvailabilityDTO(false), reservation.getId(), token);
            reservation.setDeleteFlag(true);
            reservationRepository.save(reservation);
        }

    }


}
