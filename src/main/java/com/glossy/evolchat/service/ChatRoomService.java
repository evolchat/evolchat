package com.glossy.evolchat.service;

import com.glossy.evolchat.model.ChatRoom;
import com.glossy.evolchat.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public List<ChatRoom> findAll() {
        return chatRoomRepository.findAll();
    }

    public Optional<ChatRoom> findById(int id) {
        return chatRoomRepository.findById(id);
    }

    public ChatRoom createChatRoom(String roomName, boolean isPrivate) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomName(roomName);
        chatRoom.setPrivate(isPrivate);
        return chatRoomRepository.save(chatRoom);
    }

    public void deleteChatRoom(int id) {
        chatRoomRepository.deleteById(id);
    }
}
