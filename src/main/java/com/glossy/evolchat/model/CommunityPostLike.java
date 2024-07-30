package com.glossy.evolchat.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "community_post_likes")
public class CommunityPostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(name = "post_id", nullable = false)
    private int postId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}