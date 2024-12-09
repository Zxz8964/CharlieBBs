package com.itvictorkile.service.impl;

import com.itvictorkile.dao.MessageRepository;
import com.itvictorkile.entity.Message;
import com.itvictorkile.entity.User;
import com.itvictorkile.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void sendMessage(Long senderId, Long receiverId, String content) {
        Message message = new Message();
        message.setSender(new User(senderId));
        message.setReceiver(new User(receiverId));
        message.setContent(content);
        messageRepository.save(message);
    }

    @Override
    public List<Message> getUserMessages(Long userId) {
        return messageRepository.findByReceiverIdOrderByCreatedAtDesc(userId);
    }
}
