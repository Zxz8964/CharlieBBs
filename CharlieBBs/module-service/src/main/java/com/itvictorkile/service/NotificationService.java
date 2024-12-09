package com.itvictorkile.service;

import com.itvictorkile.dto.NotificationRequest;
import com.itvictorkile.entity.Notification;

import java.util.List;

public interface NotificationService {
    Notification sendNotification(NotificationRequest request); // 返回发送的通知
    List<Notification> getUserNotifications(Long userId);
    void markAsRead(Long notificationId);
}