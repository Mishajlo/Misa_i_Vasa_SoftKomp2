package com.survey.users.NotificationService.repository;

import com.survey.users.NotificationService.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
