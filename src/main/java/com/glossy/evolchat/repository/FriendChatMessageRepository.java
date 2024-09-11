package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.FriendChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendChatMessageRepository extends JpaRepository<FriendChatMessage, Integer> {
    List<FriendChatMessage> findByChatRoomId(int chatRoomId);
}
