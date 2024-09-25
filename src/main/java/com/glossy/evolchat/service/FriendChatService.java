package com.glossy.evolchat.service;

import com.glossy.evolchat.dto.FriendChatRoomDTO;
import com.glossy.evolchat.model.FriendChatMessage;
import com.glossy.evolchat.model.FriendChatRoom;
import com.glossy.evolchat.model.User;
import com.glossy.evolchat.repository.FriendChatRoomRepository;
import com.glossy.evolchat.repository.FriendChatMessageRepository;
import com.glossy.evolchat.repository.UserRepository;
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

    @Autowired
    private final UserRepository userRepository;
    public FriendChatService(FriendChatRoomRepository friendChatRoomRepository, FriendChatMessageRepository friendChatMessageRepository, UserRepository userRepository) {
        this.friendChatRoomRepository = friendChatRoomRepository;
        this.friendChatMessageRepository = friendChatMessageRepository;
        this.userRepository = userRepository;
    }

    // 채팅방 생성
    public FriendChatRoom createChatRoom(int user1Id, int user2Id) {
        FriendChatRoom chatRoom = new FriendChatRoom();
        chatRoom.setUser1Id(user1Id);
        chatRoom.setUser2Id(user2Id);
        chatRoom.setRoomName(user1Id + " and " + user2Id); // 적절한 방 이름 설정
        return friendChatRoomRepository.save(chatRoom);
    }

    // 메시지 저장
    public FriendChatMessage saveMessage(FriendChatMessage message) {
        return friendChatMessageRepository.save(message);
    }

    // 특정 사용자의 채팅방 검색 (두 사용자의 ID로)
    public Optional<FriendChatRoom> findChatRoom(int user1Id, int user2Id) {
        return friendChatRoomRepository.findByUserIds(user1Id, user2Id);
    }

    // 특정 사용자의 모든 채팅방 조회
    public List<FriendChatRoomDTO> getUserChatRooms(int userId) {
        List<FriendChatRoom> chatRooms = friendChatRoomRepository.findByUser1IdOrUser2Id(userId, userId);
        List<FriendChatRoomDTO> chatRoomDTOs = new ArrayList<>();

        for (FriendChatRoom chatRoom : chatRooms) {
            // 채팅방의 최근 메시지 조회
            List<FriendChatMessage> recentMessages = friendChatMessageRepository.findByChatRoomIdOrderByTimestampAsc(chatRoom.getId());
            FriendChatRoomDTO chatRoomDTO = new FriendChatRoomDTO();
            chatRoomDTO.setId(chatRoom.getId());

            // 상대방의 ID에 따라 룸 이름 설정
            if (chatRoom.getUser1Id() == userId) {
                // user2의 닉네임을 가져오는 로직
                Optional<User> user2Optional = userRepository.findById(chatRoom.getUser2Id());
                user2Optional.ifPresent(user -> chatRoomDTO.setRoomName(user.getNickname()));
            } else {
                // user1의 닉네임을 가져오는 로직
                Optional<User> user1Optional = userRepository.findById(chatRoom.getUser1Id());
                user1Optional.ifPresent(user -> chatRoomDTO.setRoomName(user.getNickname()));
            }

            chatRoomDTO.setRecentMessages(recentMessages);
            chatRoomDTOs.add(chatRoomDTO);
        }
        return chatRoomDTOs;
    }

}
