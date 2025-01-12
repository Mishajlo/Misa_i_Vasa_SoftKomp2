package com.survey.users.NotificationService.repository;

import com.survey.users.NotificationService.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findAllByRecipientId(long receiverId);

}
