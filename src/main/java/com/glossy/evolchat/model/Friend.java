package com.glossy.evolchat.model;

import lombok.Data;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId1;
    private Integer userId2;
    private boolean isBlocked; // 차단 상태를 나타내는 필드
    private int friendId;
}