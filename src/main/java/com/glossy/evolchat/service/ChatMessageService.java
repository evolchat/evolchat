package com.glossy.evolchat.service;

import com.glossy.evolchat.model.ChatMessage;
import com.glossy.evolchat.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public List<ChatMessage> getRecentMessages(int count) {
        return chatMessageRepository.findTopByOrderByTimestampDesc(count);
    }

    // 새로운 메시지 저장하기
    public void saveMessage(ChatMessage message) {
        // timestamp 필드 설정
        message.setTimestamp(LocalDateTime.now());
        chatMessageRepository.save(message);
    }
}