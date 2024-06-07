package com.glossy.evolchat.service;

import com.glossy.evolchat.dto.CommentDto;
import com.glossy.evolchat.model.Comment;
import com.glossy.evolchat.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<CommentDto> getAllComments() {
        return commentRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public CommentDto getCommentById(int id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        return comment != null ? convertToDto(comment) : null;
    }

    public CommentDto saveComment(CommentDto commentDto) {
        Comment comment = convertToEntity(commentDto);
        Comment savedComment = commentRepository.save(comment);
        return convertToDto(savedComment);
    }

    public void deleteComment(int id) {
        commentRepository.deleteById(id);
    }

    private CommentDto convertToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentId(comment.getCommentId());
        commentDto.setPostId(comment.getPostId());
        commentDto.setUserId(comment.getUserId());
        commentDto.setContent(comment.getContent());
        commentDto.setCreatedAt(comment.getCreatedAt().toString());
        commentDto.setUpdatedAt(comment.getUpdatedAt().toString());
        return commentDto;
    }

    private Comment convertToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setCommentId(commentDto.getCommentId());
        comment.setPostId(commentDto.getPostId());
        comment.setUserId(commentDto.getUserId());
        comment.setContent(commentDto.getContent());
        // Handle timestamps if necessary
        return comment;
    }
}
