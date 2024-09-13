package com.glossy.evolchat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class FriendChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Long 대신 int 사용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private FriendChatRoom chatRoom; // 채팅방과의 연관 관계 설정

    @Column(nullable = false)
    private int senderId; // int 타입 사용

    @Column(nullable = false, length = 1000)
    private String message;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    // 기본 생성자
    public FriendChatMessage() {
    }

    // 생성자
    public FriendChatMessage(FriendChatRoom chatRoom, int senderId, String message) {
        this.chatRoom = chatRoom;
        this.senderId = senderId;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    // 추가된 메서드
    public void setChatRoomId(int chatRoomId) {
        // 채팅방 ID를 설정하는 메서드 (chatRoom 객체를 통해 처리하는 것이 일반적이지만,
        // 별도로 ID를 설정하고 싶다면 이 메서드를 구현할 수 있습니다)
        this.chatRoom = new FriendChatRoom(); // 새로운 FriendChatRoom 객체를 생성하거나 기존 객체를 설정
        this.chatRoom.setId(chatRoomId);
    }

    public void setText(String text) {
        this.message = text;
    }
}
