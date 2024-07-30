package com.glossy.evolchat.service;

import com.glossy.evolchat.model.CommunityPostLike;
import com.glossy.evolchat.repository.CommunityPostLikeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityPostLikeService {
    @Autowired
    private CommunityPostLikeRepository communityPostLikeRepository;

    public long countLikesByPostId(int postId) {
        return communityPostLikeRepository.countByPostId(postId);
    }

    public List<CommunityPostLike> getAllLikes() {
        return communityPostLikeRepository.findAll();
    }

    public CommunityPostLike getLikeById(int id) {
        return communityPostLikeRepository.findById(id).orElse(null);
    }

    public CommunityPostLike saveLike(CommunityPostLike communityPostLike) {
        return communityPostLikeRepository.save(communityPostLike);
    }

    public void deleteLike(int id) {
        communityPostLikeRepository.deleteById(id);
    }

}
