package com.glossy.evolchat.controller;

import com.glossy.evolchat.dto.CreateChatRoomRequest;
import com.glossy.evolchat.dto.FriendChatMessageDTO;
import com.glossy.evolchat.dto.FriendChatMessageRequest;
import com.glossy.evolchat.dto.FriendChatRoomDTO;
import com.glossy.evolchat.model.*;
import com.glossy.evolchat.service.FriendChatService;
import com.glossy.evolchat.service.FriendChatMessageService;
import com.glossy.evolchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/friend-chat")
public class FriendChatController {

    @Autowired
    private FriendChatService friendChatService;

    @Autowired
    private UserService userService;

    @Autowired
    private FriendChatMessageService friendChatMessageService;

    private final SimpMessagingTemplate messagingTemplate;

    public FriendChatController(FriendChatMessageService friendChatMessageService, SimpMessagingTemplate messagingTemplate) {
        this.friendChatMessageService = friendChatMessageService;
        this.messagingTemplate = messagingTemplate;
    }

    // 방 생성 요청
    @PostMapping("/create-chat-room")
    public ResponseEntity<FriendChatRoom> createChatRoom(@RequestBody CreateChatRoomRequest request, Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());

        // 이미 존재하는 채팅방을 확인
        Optional<FriendChatRoom> existingChatRoom = friendChatService.findChatRoom(currentUser.getId(), request.getUser2Id());
        if (existingChatRoom.isPresent()) {
            // 이미 방이 있으면 해당 방을 반환
            return ResponseEntity.ok(existingChatRoom.get());
        }

        // 방이 없으면 새로 생성
        FriendChatRoom newChatRoom = friendChatService.createChatRoom(currentUser.getId(), request.getUser2Id());
        return ResponseEntity.ok(newChatRoom);
    }

    // 메시지 전송 요청
    @PostMapping("/send-message")
    public ResponseEntity<Void> sendMessage(@RequestBody FriendChatMessageRequest messageRequest) {
        Optional<FriendChatRoom> chatRoomOpt = friendChatService.findChatRoom(messageRequest.getSenderId(), messageRequest.getReceiverId());
        if (chatRoomOpt.isPresent()) {
            FriendChatMessage message = new FriendChatMessage();
            message.setChatRoom(chatRoomOpt.get());  // 채팅방 설정
            message.setSenderId(messageRequest.getSenderId());
            message.setMessage(messageRequest.getMessage());

            try {
                friendChatMessageService.saveMessage(message);
                messagingTemplate.convertAndSend("/topic/friend-chat/" + chatRoomOpt.get().getId(), message);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                e.printStackTrace();  // 로그 출력 (개발 중 디버깅에 유용)
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user-chats")
    public ResponseEntity<List<FriendChatRoomDTO>> getUserChatRooms(Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());
        List<FriendChatRoomDTO> chatRooms = friendChatService.getUserChatRooms(currentUser.getId());
        return ResponseEntity.ok(chatRooms);
    }

    // 특정 채팅방의 메시지 조회
    @GetMapping("/messages/{chatRoomId}")
    public ResponseEntity<List<FriendChatMessageDTO>> getChatMessages(@PathVariable("chatRoomId") int chatRoomId) {
        List<FriendChatMessageDTO> messages = friendChatMessageService.getMessagesByChatRoom(chatRoomId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/unread-count/{chatRoomId}")
    public ResponseEntity<Integer> getUnreadMessageCount(@PathVariable("chatRoomId") int chatRoomId, Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());
        int unreadCount = friendChatMessageService.getUnreadMessageCount(currentUser.getId(), chatRoomId);
        return ResponseEntity.ok(unreadCount);
    }

    @PostMapping("/update-unread/{chatRoomId}")
    public ResponseEntity<Void> updateUnreadMessages(@PathVariable int chatRoomId, Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());
        friendChatMessageService.updateUnreadMessages(currentUser.getId(), chatRoomId);
        return ResponseEntity.ok().build();
    }

    // 웹소켓을 통한 메시지 전송
    @MessageMapping("/send/{chatRoomId}")
    @SendTo("/topic/friend-chat/{chatRoomId}")
    public FriendChatMessage sendWebSocketMessage(
            @DestinationVariable("chatRoomId") int chatRoomId,
            Principal principal,
            @Payload Map<String, Object> payload
    ) {
        String username = principal.getName();
        User user = userService.findByUsername(username);

        String text = (String) payload.get("text");

        int senderId = user.getId();

        // FriendChatMessage 객체 생성 및 데이터 설정
        FriendChatMessage message = new FriendChatMessage();
        message.setText(text);
        message.setChatRoomId(chatRoomId);
        message.setSenderId(senderId);

        // 메시지 저장 서비스 호출
        friendChatMessageService.saveMessage(message);

        // 메시지를 클라이언트로 전송
        return message;
    }
}
