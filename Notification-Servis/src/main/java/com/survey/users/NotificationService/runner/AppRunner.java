package com.survey.users.NotificationService.runner;

import com.survey.users.NotificationService.domain.NotificationType;
import com.survey.users.NotificationService.repository.NotificationTypeRepository;
import com.survey.users.NotificationService.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class AppRunner {

    private NotificationTypeRepository notificationTypeRepository;
    private NotificationService notificationService;

    @Bean
    CommandLineRunner loadData() {
        return args -> {
            NotificationType notificationType = new NotificationType();
            notificationType.setType("activation");
            notificationType.setSubject("Registration confirmation");
            notificationType.setContent("Postovani %s %s aktivaciju naloga %s mozete postici klikom na sledeci link <a href=\"http://localhost:8080\" style=\"text-decoration: underline; color: blue;\">%s</a>");
            notificationTypeRepository.save(notificationType);

            NotificationType rezervacijaNapravljena = new NotificationType();
            rezervacijaNapravljena.setType("reservation");
            rezervacijaNapravljena.setSubject("Reservation confirmation");
            rezervacijaNapravljena.setContent("Postovani, vasa rezervacija za restoran %s za sto %s datuma %s je potvrdjena. Pozz");
            notificationTypeRepository.save(rezervacijaNapravljena);

            NotificationType loyalty = new NotificationType();
            loyalty.setType("achievement");
            loyalty.setSubject("Achievement confirmation");
            loyalty.setContent("Postovani, ostvarili ste %s sto znaci %s. Super za vas!");
            notificationTypeRepository.save(loyalty);

            NotificationType manager_loyalty = new NotificationType();
            manager_loyalty.setType("loyalty");
            manager_loyalty.setSubject("Someone Got Something");
            manager_loyalty.setContent("Postovani, Korisnik %s je ostvario %s sto znaci da ima %s rezervacija i da %s!");
            notificationTypeRepository.save(manager_loyalty);

            NotificationType client_cancel_reservation = new NotificationType();
            client_cancel_reservation.setType("client_cancel_reservation");
            client_cancel_reservation.setSubject("Client Cancellation");
            client_cancel_reservation.setContent("Postovani, klijent na adresi: %s je otkazao porudzbinu datuma %s u restoranu %s!");
            notificationTypeRepository.save(client_cancel_reservation);

            NotificationType manager_cancel_reservation = new NotificationType();
            manager_cancel_reservation.setType("manager_cancel_reservation");
            manager_cancel_reservation.setSubject("Manager Cancellation");
            manager_cancel_reservation.setContent("Postovani, vasa rezervacija u restoranu %s za datum %s je otkazana. Sucks to suck!");
            notificationTypeRepository.save(manager_cancel_reservation);

            NotificationType password_change = new NotificationType();
            password_change.setType("password_change");
            password_change.setSubject("Password Change");
            password_change.setContent("Postovani, vasa nova lozinka za nalog %s je %s!");
            notificationTypeRepository.save(manager_cancel_reservation);

            NotificationType reminder = new NotificationType();
            reminder.setType("reminder");
            reminder.setSubject("Reservation Reminder");
            reminder.setContent("Postovani, podsecamo vas da je vasa rezervacija u restoranu %s datuma %s za 24h! See ya!");
            notificationTypeRepository.save(reminder);
        };
    }
}
