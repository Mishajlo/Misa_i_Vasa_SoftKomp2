package com.survey.users.NotificationService.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notification_types")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationType extends BaseEntity{

    private String type;
    private String subject;
    private String content;

}
