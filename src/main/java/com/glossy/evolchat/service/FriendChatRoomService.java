package com.glossy.evolchat.service;

import com.glossy.evolchat.dto.FriendChatRoomDTO;
import com.glossy.evolchat.model.FriendChatMessage;
import com.glossy.evolchat.model.FriendChatRoom;
import com.glossy.evolchat.repository.FriendChatMessageRepository;
import com.glossy.evolchat.repository.FriendChatRoomRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FriendChatRoomService {

    @Autowired
    private FriendChatRoomRepository friendChatRoomRepository;

    @Autowired
    private FriendChatMessageRepository friendChatMessageRepository;

    public List<FriendChatRoomDTO> getUserChatRooms(int userId) {
        List<FriendChatRoom> chatRooms = friendChatRoomRepository.findByUser1Id(userId);
        List<FriendChatRoomDTO> chatRoomDTOs = new ArrayList<>();

        for (FriendChatRoom chatRoom : chatRooms) {
            List<FriendChatMessage> recentMessages = friendChatMessageRepository.findByChatRoomId(chatRoom.getId());
            FriendChatRoomDTO chatRoomDTO = new FriendChatRoomDTO();
            chatRoomDTO.setId(chatRoom.getId());
            chatRoomDTO.setRoomName(chatRoom.getRoomName());
            chatRoomDTO.setRecentMessages(recentMessages);
            chatRoomDTOs.add(chatRoomDTO);
        }
        return chatRoomDTOs;
    }
}