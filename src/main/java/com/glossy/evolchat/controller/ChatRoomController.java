package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.ChatRoom;
import com.glossy.evolchat.service.ChatRoomService;
import com.glossy.evolchat.dto.CreateChatRoomRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatrooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @GetMapping
    public List<ChatRoom> getChatRooms() {
        return chatRoomService.getChatRooms();
    }

    @PostMapping("/create")
    public ChatRoom createChatRoom(@RequestBody CreateChatRoomRequest request) {
        return chatRoomService.createChatRoom(request.getName());
    }
}
