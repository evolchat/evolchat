package com.glossy.evolchat.dto;

import com.glossy.evolchat.model.FriendChatMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FriendChatRoomDTO {
    private int id;
    private String roomName;
    private List<FriendChatMessage> recentMessages; // 최신 메시지 목록
}
