package com.glossy.evolchat.dto;

import lombok.Data;

@Data
public class PostDto {
    private int postId;
    private int userId;
    private int boardId;
    private String title;
    private String content;
}
