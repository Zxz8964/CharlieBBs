package com.itvictorkile.api;

import com.itvictorkile.dto.NotificationDTO;
import com.itvictorkile.dto.NotificationRequest;
import com.itvictorkile.entity.Notification;
import com.itvictorkile.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // 获取用户的所有通知
    @GetMapping("/user/{userId}")
    public List<NotificationDTO> getUserNotifications(@PathVariable Long userId) {
        return notificationService.getUserNotifications(userId).stream()
                .map(notification -> new NotificationDTO(notification))
                .collect(Collectors.toList());
    }

    // 标记通知为已读
    @PostMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok().build();
    }

    // 发送通知并通过 WebSocket 推送给接收者
    @PostMapping("/send")
    public ResponseEntity<Void> sendNotification(@RequestBody NotificationRequest request) {
        Notification notification = notificationService.sendNotification(request); // 调用服务层方法

        // 推送通知到指定接收者的 WebSocket 频道
        messagingTemplate.convertAndSendToUser(
                request.getReceiverUsername(), "/queue/notifications", new NotificationDTO(notification)
        );

        return ResponseEntity.ok().build();
    }
}