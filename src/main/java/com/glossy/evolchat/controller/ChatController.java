package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.ChatMessage;
import com.glossy.evolchat.service.ChatMessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(ChatMessageService chatMessageService, SimpMessagingTemplate messagingTemplate) {
        this.chatMessageService = chatMessageService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/history")
    public List<ChatMessage> getChatHistory() {
        return chatMessageService.getRecentMessages(50);
    }

    @MessageMapping("/send") // 클라이언트에서 /app/send로 메시지를 보내면
    @SendTo("/topic/chat") // 모든 구독자에게 /topic/chat으로 메시지를 전송
    public ChatMessage sendMessage(ChatMessage message) {
        chatMessageService.saveMessage(message); // 메시지 저장
        return message; // 클라이언트에게 다시 전송
    }
}
