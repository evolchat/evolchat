package com.glossy.evolchat.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class SupportComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    @ManyToOne
    @JoinColumn(name = "support_post_id", nullable = false)
    private SupportPost supportPost; // 고객센터 게시글과의 관계 설정

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 사용자와의 관계 설정

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
