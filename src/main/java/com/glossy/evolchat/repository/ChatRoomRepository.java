package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    ChatRoom findByChatRoomId(int chatRoomId);
}
