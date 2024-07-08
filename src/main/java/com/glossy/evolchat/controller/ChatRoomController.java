package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.ChatRoom;
import com.glossy.evolchat.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chatrooms")
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;

    @GetMapping
    public List<ChatRoom> getAllChatRooms() {
        return chatRoomService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ChatRoom> getChatRoom(@PathVariable int id) {
        return chatRoomService.findById(id);
    }

    @PostMapping
    public ChatRoom createChatRoom(@RequestBody ChatRoom chatRoom) {
        return chatRoomService.createChatRoom(chatRoom.getRoomName(), chatRoom.isPrivate());
    }

    @DeleteMapping("/{id}")
    public void deleteChatRoom(@PathVariable int id) {
        chatRoomService.deleteChatRoom(id);
    }
}
