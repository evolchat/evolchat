package com.glossy.evolchat.dto;

import com.glossy.evolchat.model.FriendChatMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FriendChatMessageDTO {
    private int id;
    private String message;
    private int senderId;
    private String timestamp;

    // 필요에 따라 추가할 필드
    private int chatRoomId;

    // 생성자, 게터 및 세터 추가
    public FriendChatMessageDTO(int id, String message, int senderId, String timestamp, int chatRoomId) {
        this.id = id;
        this.message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
        this.chatRoomId = chatRoomId;
    }
}
