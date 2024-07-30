package com.glossy.evolchat.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class CommunityPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @Column(nullable = false, length = 255)
    private String userId;

    @Column(nullable = false)
    private int boardId;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String tags;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean isDeleted = false;

    @Column(nullable = false)
    private int views = 0;

    @Column(name = "comment_count")
    private int commentCount; // 댓글 개수를 저장하는 필드

    @OneToMany(mappedBy = "communityPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityComment> communityComments; // 댓글 목록

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