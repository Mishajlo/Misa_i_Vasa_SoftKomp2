package com.survey.users.NotificationService.repository;

import com.survey.users.NotificationService.domain.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationTypeRepository extends JpaRepository<NotificationType, Long> {
    NotificationType getNotificationTypeByType(String notificationType);
}
