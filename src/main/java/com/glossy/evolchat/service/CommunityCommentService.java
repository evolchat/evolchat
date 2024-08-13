package com.glossy.evolchat.service;

import com.glossy.evolchat.model.CommunityComment;
import com.glossy.evolchat.repository.CommunityCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.glossy.evolchat.model.CommunityPost;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityCommentService {

    private final CommunityCommentRepository communityCommentRepository;

    public List<CommunityComment> getCommentsByPost(CommunityPost post) {
        return communityCommentRepository.findByCommunityPost(post);
    }

    public boolean addComment(CommunityComment comment) {
        try {
            communityCommentRepository.save(comment);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 댓글 조회 (by ID)
    public CommunityComment getCommentById(int commentId) {
        return communityCommentRepository.findById(commentId).orElse(null);
    }

    // 댓글 삭제
    public boolean deleteComment(int commentId) {
        try {
            communityCommentRepository.deleteById(commentId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // 댓글 수정
    public boolean updateComment(CommunityComment comment) {
        try {
            communityCommentRepository.save(comment);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
