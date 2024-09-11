package com.glossy.evolchat.model;

import lombok.Data;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
public class FriendChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer user1Id;

    @Column(nullable = false)
    private Integer user2Id;

    @Column(nullable = false)
    private String roomName;

    // getters and setters
}
