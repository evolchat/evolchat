package com.glossy.evolchat.service;

import com.glossy.evolchat.model.FriendChatMessage;
import com.glossy.evolchat.model.FriendChatRoom;
import com.glossy.evolchat.repository.FriendChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FriendChatMessageService {

    @Autowired
    private FriendChatMessageRepository friendChatMessageRepository;

    // 저장된 모든 메시지 가져오기
    public List<FriendChatMessage> getAllMessages() {
        return friendChatMessageRepository.findAll();
    }

    // 최근 메시지 가져오기
    public List<FriendChatMessage> getRecentMessages(int chatRoomId, int count) {
        return friendChatMessageRepository.findTopNByChatRoomIdOrderByTimestampDesc(chatRoomId, PageRequest.of(0, count));
    }

    // 특정 채팅방의 메시지 가져오기
    public List<FriendChatMessage> getMessagesByChatRoom(int chatRoomId) {
        return friendChatMessageRepository.findByChatRoomIdOrderByTimestampAsc(chatRoomId);
    }

    // 새로운 메시지 저장하기
    public void saveMessage(FriendChatMessage message) {
        // timestamp 필드 설정
        message.setTimestamp(LocalDateTime.now());
        friendChatMessageRepository.save(message);
    }
}
