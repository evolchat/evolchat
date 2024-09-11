package com.glossy.evolchat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FriendChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private FriendChatRoom chatRoom; // 채팅방과의 연관 관계 설정

    @Column(nullable = false)
    private int senderId;

    @Column(nullable = false, length = 1000)
    private String message;
}