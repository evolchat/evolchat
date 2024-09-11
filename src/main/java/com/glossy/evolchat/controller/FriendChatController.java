package com.glossy.evolchat.controller;

import com.glossy.evolchat.dto.CreateChatRoomRequest;
import com.glossy.evolchat.dto.FriendChatMessageRequest;
import com.glossy.evolchat.dto.FriendChatRoomDTO;
import com.glossy.evolchat.model.FriendChatMessage;
import com.glossy.evolchat.model.FriendChatRoom;
import com.glossy.evolchat.model.User;
import com.glossy.evolchat.service.FriendChatService;
import com.glossy.evolchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/friend-chat")
public class FriendChatController {

    @Autowired
    private FriendChatService friendChatService;

    @Autowired
    private UserService userService;

    @PostMapping("/create-chat-room")
    public ResponseEntity<FriendChatRoom> createChatRoom(@RequestBody CreateChatRoomRequest request, Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());
        FriendChatRoom chatRoom = friendChatService.createChatRoom(currentUser.getId(), request.getUser2Id());
        return ResponseEntity.ok(chatRoom);
    }

    @PostMapping("/send-message")
    public ResponseEntity<Void> sendMessage(@RequestBody FriendChatMessageRequest messageRequest) {
        Optional<FriendChatRoom> chatRoomOpt = friendChatService.findChatRoom(messageRequest.getSenderId(), messageRequest.getReceiverId());
        if (chatRoomOpt.isPresent()) {
            FriendChatMessage message = new FriendChatMessage();
            message.setChatRoom(chatRoomOpt.get());
            message.setSenderId(messageRequest.getSenderId());
            message.setMessage(messageRequest.getMessage());
            friendChatService.saveMessage(message);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user-chats")
    public ResponseEntity<List<FriendChatRoomDTO>> getUserChatRooms(Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());
        List<FriendChatRoomDTO> chatRooms = friendChatService.getUserChatRooms(currentUser.getId());
        return ResponseEntity.ok(chatRooms);
    }
}
