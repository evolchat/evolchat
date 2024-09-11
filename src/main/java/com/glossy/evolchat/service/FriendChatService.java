package com.glossy.evolchat.service;

import com.glossy.evolchat.dto.FriendChatRoomDTO;
import com.glossy.evolchat.model.FriendChatMessage;
import com.glossy.evolchat.model.FriendChatRoom;
import com.glossy.evolchat.repository.FriendChatRoomRepository;
import com.glossy.evolchat.repository.FriendChatMessageRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
public class FriendChatService {

    @Autowired
    private final FriendChatRoomRepository friendChatRoomRepository;

    @Autowired
    private final FriendChatMessageRepository friendChatMessageRepository;

    public FriendChatService(FriendChatRoomRepository friendChatRoomRepository, FriendChatMessageRepository friendChatMessageRepository) {
        this.friendChatRoomRepository = friendChatRoomRepository;
        this.friendChatMessageRepository = friendChatMessageRepository;
    }

    public FriendChatRoom createChatRoom(int user1Id, int user2Id) {
        FriendChatRoom chatRoom = new FriendChatRoom();
        chatRoom.setUser1Id(user1Id);
        chatRoom.setUser2Id(user2Id);
        chatRoom.setRoomName("Chat Room between " + user1Id + " and " + user2Id); // 적절한 방 이름 설정
        return friendChatRoomRepository.save(chatRoom);
    }

    public List<FriendChatMessage> getChatMessages(int chatRoomId) {
        return friendChatMessageRepository.findByChatRoomId(chatRoomId);
    }

    public FriendChatMessage saveMessage(FriendChatMessage message) {
        return friendChatMessageRepository.save(message);
    }

    public Optional<FriendChatRoom> findChatRoom(int user1Id, int user2Id) {
        return friendChatRoomRepository.findByUser1IdAndUser2Id(user1Id, user2Id);
    }

    public List<FriendChatRoomDTO> getUserChatRooms(int userId) {
        List<FriendChatRoom> chatRooms = friendChatRoomRepository.findByUser1IdOrUser2Id(userId, userId);
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
