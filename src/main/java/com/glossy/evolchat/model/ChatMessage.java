package com.glossy.evolchat.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String chatRoomId; // 새 필드 추가
    private String sender;
    private String content;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    // 생성자
    public ChatMessage(String chatRoomId, String sender, String content) {
        this.chatRoomId = chatRoomId;
        this.sender = sender;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }
}
