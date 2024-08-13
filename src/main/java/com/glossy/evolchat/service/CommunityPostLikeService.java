package com.glossy.evolchat.service;

import com.glossy.evolchat.model.CommunityPostLike;
import com.glossy.evolchat.repository.CommunityPostLikeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommunityPostLikeService {

    @Autowired
    private CommunityPostLikeRepository communityPostLikeRepository;

    // 모든 좋아요 가져오기
    public List<CommunityPostLike> getAllLikes() {
        return communityPostLikeRepository.findAll();
    }

    // 특정 ID의 좋아요 가져오기
    public CommunityPostLike getLikeById(int id) {
        return communityPostLikeRepository.findById(id).orElse(null);
    }

    // 좋아요 저장
    public CommunityPostLike saveLike(CommunityPostLike communityPostLike) {
        return communityPostLikeRepository.save(communityPostLike);
    }

    // 좋아요 삭제
    public void deleteLike(int id) {
        communityPostLikeRepository.deleteById(id);
    }

    // 좋아요 토글 (좋아요/좋아요 취소)
    public boolean toggleLike(int postId, String userId) {
        Optional<CommunityPostLike> existingLikeOptional = communityPostLikeRepository.findByPostIdAndUserId(postId, userId);

        if (existingLikeOptional.isPresent()) {
            // 기존 좋아요가 있는 경우 삭제 (좋아요 취소)
            communityPostLikeRepository.delete(existingLikeOptional.get());
            return false; // 좋아요 취소
        } else {
            // 좋아요가 없는 경우 추가
            CommunityPostLike newLike = new CommunityPostLike();
            newLike.setPostId(postId);
            newLike.setUserId(userId);
            communityPostLikeRepository.save(newLike);
            return true; // 좋아요 추가
        }
    }

    // 특정 게시물의 좋아요 수 가져오기
    public int getLikeCountByPostId(int postId) {
        return (int) communityPostLikeRepository.countByPostId(postId);
    }
}