package com.glossy.evolchat.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class CommunityComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Ensure this is the only AUTO_INCREMENT column

    @ManyToOne(cascade = CascadeType.PERSIST) // CascadeType.PERSIST to ensure CommunityPost is saved
    @JoinColumn(name = "post_id", nullable = false)
    private CommunityPost communityPost;

    @ManyToOne(cascade = CascadeType.PERSIST) // CascadeType.PERSIST to ensure User is saved
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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
