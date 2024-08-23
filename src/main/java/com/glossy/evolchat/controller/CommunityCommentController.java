package com.glossy.evolchat.controller;

import com.glossy.evolchat.dto.CommunityCommentDto;
import com.glossy.evolchat.model.CommunityComment;
import com.glossy.evolchat.model.CommunityPost;
import com.glossy.evolchat.model.User;
import com.glossy.evolchat.service.CommunityCommentService;
import com.glossy.evolchat.service.CommunityPostService;
import com.glossy.evolchat.service.UserService; // Assuming you have a UserService to fetch user details
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommunityCommentController {

    private final CommunityCommentService communityCommentService;
    private final CommunityPostService communityPostService;
    private final UserService userService; // Service to get user details

    // 댓글 목록 조회
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommunityComment>> getCommentsByPostId(@PathVariable int postId) {
        CommunityPost communityPost = communityPostService.getPostById(postId);
        if (communityPost != null) {
            List<CommunityComment> comments = communityCommentService.getCommentsByPost(communityPost);
            return new ResponseEntity<>(comments, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 댓글 추가
    @PostMapping
    public ResponseEntity<Void> addComment(@RequestBody CommunityCommentDto commentRequest, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username); // Assuming you have a method to get User by username

        // CommunityPost 객체를 데이터베이스에서 조회
        CommunityPost communityPost = communityPostService.getPostById(commentRequest.getPostId());
        if (communityPost == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // CommunityComment 객체 설정
        CommunityComment comment = new CommunityComment();
        comment.setContent(commentRequest.getContent());
        comment.setCommunityPost(communityPost);
        comment.setUser(user);

        boolean success = communityCommentService.addComment(comment);
        if (success) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable int commentId) {
        boolean success = communityCommentService.deleteComment(commentId);
        if (success) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable int commentId, @RequestBody CommunityCommentDto commentRequest) {
        CommunityComment existingComment = communityCommentService.getCommentById(commentId);
        if (existingComment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        existingComment.setContent(commentRequest.getContent());

        boolean success = communityCommentService.updateComment(existingComment);
        if (success) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
