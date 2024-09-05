package com.glossy.evolchat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class GoldChipPointsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(nullable = false)
    private String activityType; // 활동의 종류 (예: 환전, 보냄, 받음, 구매)

    @Column(nullable = true)
    private String target; // 대상 (예: 나쁜녀석들마동석, 베테랑 등) -> null 가능

    @Column(nullable = false)
    private int quantity; // 수량 (예: 10,000)

    @Column(nullable = false)
    private int remainingQuantity; // 남은 수량 (예: 10,500)

    @Column(nullable = true)
    private Integer usedChip; // 사용 캐시 (예: 1,000,000 원) -> null 가능

    @Column(nullable = true)
    private String exchangedAmount; // 환전 금액 (예: 767,000 원) -> null 가능

    @Column(nullable = false)
    private LocalDateTime createdAt; // 활동 일시
}