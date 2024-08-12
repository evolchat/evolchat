package com.glossy.evolchat.service;

import com.glossy.evolchat.model.CommunityComment;
import com.glossy.evolchat.repository.CommunityCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.glossy.evolchat.model.CommunityPost;

import java.util.List;
import java.util.Optional;

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

    public boolean deleteComment(int commentId) {
        try {
            if (communityCommentRepository.existsById(commentId)) {
                communityCommentRepository.deleteById(commentId);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateComment(int commentId, CommunityComment updatedComment) {
        try {
            Optional<CommunityComment> existingCommentOpt = communityCommentRepository.findById(commentId);
            if (existingCommentOpt.isPresent()) {
                CommunityComment existingComment = existingCommentOpt.get();
                existingComment.setContent(updatedComment.getContent());
                // You might want to update `updatedAt` if you have it in your model
                communityCommentRepository.save(existingComment);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
