package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.ChatMessage;
import com.glossy.evolchat.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat-messages")
public class ChatMessageController {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @GetMapping("/{chatRoomId}")
    public List<ChatMessage> getMessages(@PathVariable String chatRoomId) {
        return chatMessageRepository.findByChatRoomId(chatRoomId);
    }

    @PostMapping
    public ChatMessage sendMessage(@RequestBody ChatMessage message) {
        return chatMessageRepository.save(message);
    }
}
