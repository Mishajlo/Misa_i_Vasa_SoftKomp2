package com.survey.users.NotificationService.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Notification extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "type_id")
    private NotificationType type;
    private String recipientEmail;
    private long recipientId;
    private String senderEmail;
    private long senderId;
    private LocalDateTime timestamp;

}
