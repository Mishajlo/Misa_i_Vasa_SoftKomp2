package com.example.Rezervacije_Servis.service;

import com.example.Rezervacije_Servis.domain.entities.Reservation;
import com.example.Rezervacije_Servis.dto.QueueDTOs.NotifServiceDTO;
import com.example.Rezervacije_Servis.queues.SendToNotification;
import com.example.Rezervacije_Servis.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SchedulerImpl {
    private ReservationRepository reservationRepository;
    private SendToNotification sendToNotification;

    @Autowired
    public SchedulerImpl(ReservationRepository reservationRepository, SendToNotification sendToNotification) {
        this.reservationRepository = reservationRepository;
        this.sendToNotification = sendToNotification;
    }

    @Scheduled(fixedRate = 30 * 1000) // 30 minutes in milliseconds - 30 * 60 * 1000
    @Transactional
    public void processReservations() {
        LocalDateTime now = LocalDateTime.now();

        sendUpcomingReservationReminders(now);

        updateCompletedReservations(now);
    }

    private void sendUpcomingReservationReminders(LocalDateTime now) {
        LocalDateTime tommorow = now.plusHours(24);

        List<Reservation> toCheck = reservationRepository.reminderQuery();

        List<Reservation> upcomingReservations = new ArrayList<>();

        for (Reservation reservation : toCheck) {
            //LocalDateTime resTime = reservation.getTimeslot().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime resTime = reservation.getDate().atTime(reservation.getStartTime());
//            resTime = resTime.plusHours(reservation.getTimeslot().getStartTime().getHour());
            if (resTime.equals(tommorow)) {
                upcomingReservations.add(reservation);
            }
        }

        for (Reservation reservation : upcomingReservations) {
            try {
                NotifServiceDTO notification = new NotifServiceDTO();
                notification.setNotifType("reminder");
                notification.setRecipientEmail(reservation.getUserData().getEmail());
                notification.setRecipientId(reservation.getUserData().getUserId());
                notification.getParams().add(reservation.getTable().getRestaurant().getName());
                notification.getParams().add(reservation.getDate().toString());


                sendToNotification.sendNotification(notification);

                reservation.setReminder(true);
                reservationRepository.save(reservation);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateCompletedReservations(LocalDateTime now) {
        List<Reservation> toCheck = reservationRepository.findAllByDeleteFlagFalse();

        List<Reservation> completedReservations = new ArrayList<>();
        for (Reservation reservation : toCheck) {
            //LocalDateTime resTime = reservation.getTimeslot().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime resTime = reservation.getDate().atTime(reservation.getStartTime());
            //resTime = resTime.plusHours(reservation.getTimeslot().getEndTime().getHour());
            if (resTime.isBefore(now)) {
                completedReservations.add(reservation);
            }
        }

        for (Reservation reservation : completedReservations) {
            try {
                reservation.setDeleteFlag(true);
                reservationRepository.save(reservation);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}