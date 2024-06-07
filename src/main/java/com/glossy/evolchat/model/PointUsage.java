package com.glossy.evolchat.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PointUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(nullable = false)
    private int usageId;

    @Column(nullable = false)
    private int userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PointType pointType;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private LocalDateTime usageDate;

    @Column(nullable = false)
    private String purpose;

    // Getters and Setters
}
