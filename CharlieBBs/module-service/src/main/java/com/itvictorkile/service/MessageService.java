package com.itvictorkile.service;

import com.itvictorkile.entity.Message;

import java.util.List;

public interface MessageService {
    void sendMessage(Long senderId, Long receiverId, String content);
    List<Message> getUserMessages(Long userId);
}
