package com.glossy.evolchat.service;

import com.glossy.evolchat.model.ChatRoom;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatRoomService {

    private final List<ChatRoom> chatRooms = new ArrayList<>();

    // Initialize with some dummy data
    public ChatRoomService() {
        for (int i = 1; i <= 20; i++) {
            chatRooms.add(new ChatRoom(
                    null,  // id will be auto-generated
                    "코리아 스피드 바카라 " + i,
                    "🤩100출 500마감 프로젝트🤩100출 500마감 프로젝트🤩100출 500마감 프로젝트",
                    582, 485, 4856
            ));
        }
    }

    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public ChatRoom createChatRoom(String name) {
        ChatRoom newRoom = new ChatRoom(
                null,  // id will be auto-generated
                name,
                "새로 개설된 채팅방입니다.",
                0, 0, 0
        );
        chatRooms.add(newRoom);
        return newRoom;
    }
}
