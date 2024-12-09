package com.itvictorkile.dto;

import com.itvictorkile.entity.Notification;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class NotificationDTO {
    private Long id;
    private Long userId; // 接收者ID
    private String type;
    private String message;
    private String link;
    private Boolean isRead;
    private Timestamp createdAt;

    public NotificationDTO(Notification notification) {
        this.id = notification.getId();
        this.userId = notification.getUser().getId();
        this.type = notification.getType();
        this.message = notification.getMessage();
        this.link = notification.getLink();
        this.isRead = notification.getIsRead();
        this.createdAt = notification.getCreatedAt();
    }
}