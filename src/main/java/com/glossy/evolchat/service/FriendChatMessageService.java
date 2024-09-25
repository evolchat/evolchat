package com.glossy.evolchat.service;

import com.glossy.evolchat.dto.FriendChatMessageDTO;
import com.glossy.evolchat.model.FriendChatMessage;
import com.glossy.evolchat.model.FriendChatRoom;
import com.glossy.evolchat.repository.FriendChatMessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<FriendChatMessageDTO> getMessagesByChatRoom(int chatRoomId) {
        List<FriendChatMessage> messages = friendChatMessageRepository.findByChatRoomIdOrderByTimestampAsc(chatRoomId);

        // 엔티티를 DTO로 변환
        return messages.stream().map(message -> new FriendChatMessageDTO(
                message.getId(),
                message.getMessage(),
                message.getSenderId(),
                message.getTimestamp().toString(),  // DateTime을 문자열로 변환
                message.getChatRoom().getId()       // 필요시 추가
        )).collect(Collectors.toList());
    }

    public List<FriendChatMessage> getUnreadMessages(int userId, int chatRoomId) {
        return friendChatMessageRepository.findUnreadMessages(userId, chatRoomId);
    }

    @Transactional
    public void updateUnreadMessages(int userId, int chatRoomId) {
        friendChatMessageRepository.markMessagesAsRead(userId, chatRoomId);
    }

    // 읽지 않은 메시지 갯수 반환
    public int getUnreadMessageCount(int userId, int chatRoomId) {
        return friendChatMessageRepository.countUnreadMessages(userId, chatRoomId);
    }

    // 새로운 메시지 저장하기
    public void saveMessage(FriendChatMessage message) {
        // timestamp 필드 설정
        message.setTimestamp(LocalDateTime.now());
        friendChatMessageRepository.save(message);
    }
}
