package com.glossy.evolchat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendChatMessageRequest {
    private Long chatRoomId; // 채팅방 ID
    private int senderId; // 발신자 ID
    private int receiverId; // 발신자 ID
    private String message; // 메시지 내용
}
