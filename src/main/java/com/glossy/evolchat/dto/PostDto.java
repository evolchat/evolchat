package com.glossy.evolchat.dto;

import lombok.Data;

@Data
public class PostDto {
    private int postId;
    private String userId;
    private int boardId;
    private String title;
    private String content;
    private long likeCount;
    private int views;
    private String createdAt;
}
