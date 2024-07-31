package com.glossy.evolchat.dto;

import lombok.Data;

@Data
public class CommunityCommentDto {
    private int commentId;
    private int postId;
    private int userId;
    private String content;
    private String createdAt;
    private String updatedAt;
}
