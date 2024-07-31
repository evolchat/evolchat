package com.glossy.evolchat.model;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chatRoomId;
    private String sender;
    private String content;

    // 기타 필요한 필드와 메서드
}
