package com.itvictorkile.dto;

import lombok.Data;

@Data
public class NotificationRequest {
    private Long senderId;         // 发送者 ID
    private Long receiverId;       // 接收者 ID
    private String content;        // 通知内容
    private String receiverUsername; // 接收者用户名，用于推送到特定用户的 WebSocket 频道
}