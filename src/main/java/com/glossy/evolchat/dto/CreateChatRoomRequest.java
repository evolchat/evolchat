package com.glossy.evolchat.dto;

import lombok.Data;

@Data
public class CreateChatRoomRequest {
    private int user1Id;
    private int user2Id;
    private String name;
}
