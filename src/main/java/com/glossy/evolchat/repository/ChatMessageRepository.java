package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query(value = "SELECT * FROM chat_message ORDER BY timestamp ASC LIMIT ?1", nativeQuery = true)
    List<ChatMessage> findTopByOrderByTimestampDesc(int count);
}
