package com.glossy.evolchat.service;

import com.glossy.evolchat.model.ChatMessage;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatMessageService {

    private final List<ChatMessage> messages = new ArrayList<>();

    // 저장된 모든 메시지 가져오기
    public List<ChatMessage> getAllMessages() {
        return new ArrayList<>(messages); // 복사본 반환
    }

    // 새로운 메시지 저장하기
    public void saveMessage(ChatMessage message) {
        messages.add(message);
    }
}
