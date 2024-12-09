package com.itvictorkile.service.impl;

import com.itvictorkile.dao.NotificationRepository;
import com.itvictorkile.dto.NotificationRequest;
import com.itvictorkile.entity.Notification;
import com.itvictorkile.entity.User;
import com.itvictorkile.service.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification sendNotification(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setUser(new User(request.getReceiverId())); // 设置接收者
        notification.setSender(new User(request.getSenderId())); // 设置发送者
        notification.setMessage(request.getContent());
        notification.setType("DEFAULT"); // 根据需要设置类型
        notification.setLink(null); // 根据需要设置链接

        return notificationRepository.save(notification); // 返回保存的通知
    }

    @Override
    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found"));
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }
}
