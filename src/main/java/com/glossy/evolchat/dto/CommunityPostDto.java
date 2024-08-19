package com.glossy.evolchat.dto;

import lombok.Data;

@Data
public class CommunityPostDto {
    private int postId;
    private String userId;
    private int boardId;
    private String title;
    private String content;
    private long likeCount;
    private long commentCount;
    private int views;
    private String createdAt;
    private String imageUrl;
}
