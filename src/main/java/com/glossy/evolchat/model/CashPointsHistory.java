package com.glossy.evolchat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class CashPointsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(nullable = false)
    private String activityType; // 활동의 종류 (게시글, 댓글, 조회수, 추천, 전환 등)

    @Column(nullable = false)
    private String content; // 활동의 내용

    @Column(nullable = false)
    private int pointsChange; // 포인트 변동 (양수 또는 음수)

    @Column(nullable = false)
    private int remainingPoint; // 남은 포인트

    @Column(nullable = false)
    private String username; // 유저명

    @Column(nullable = false)
    private LocalDateTime createdAt; // 활동 일시
}
