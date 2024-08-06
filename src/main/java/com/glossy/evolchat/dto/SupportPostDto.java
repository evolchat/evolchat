package com.glossy.evolchat.dto;

import lombok.Data;

@Data
public class SupportPostDto {
    private int postId;
    private String userId;
    private int boardId;
    private int detailedCategory;
    private String title;
    private String content;
    private int views;
    private String createdAt;
}
