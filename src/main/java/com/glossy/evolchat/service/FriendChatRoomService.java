package com.glossy.evolchat.service;

import com.glossy.evolchat.dto.FriendChatRoomDTO;
import com.glossy.evolchat.model.FriendChatMessage;
import com.glossy.evolchat.model.FriendChatRoom;
import com.glossy.evolchat.repository.FriendChatMessageRepository;
import com.glossy.evolchat.repository.FriendChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendChatRoomService {

    @Autowired
    private FriendChatRoomRepository friendChatRoomRepository;

    @Autowired
    private FriendChatMessageRepository friendChatMessageRepository;

    public List<FriendChatRoomDTO> getUserChatRooms(int userId) {
        List<FriendChatRoom> chatRooms = friendChatRoomRepository.findByUser1IdOrUser2Id(userId, userId);
        List<FriendChatRoomDTO> chatRoomDTOs = new ArrayList<>();

        for (FriendChatRoom chatRoom : chatRooms) {
            // 최신 10개의 메시지를 가져온다고 가정
            List<FriendChatMessage> recentMessages = friendChatMessageRepository.findTopNByChatRoomIdOrderByTimestampDesc(chatRoom.getId(), PageRequest.of(0, 10));

            FriendChatRoomDTO chatRoomDTO = new FriendChatRoomDTO();
            chatRoomDTO.setId(chatRoom.getId());
            chatRoomDTO.setRoomName(chatRoom.getRoomName());
            chatRoomDTO.setRecentMessages(recentMessages);
            chatRoomDTOs.add(chatRoomDTO);
        }
        return chatRoomDTOs;
    }
}
