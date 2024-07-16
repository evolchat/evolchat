package com.glossy.evolchat.service;

import com.glossy.evolchat.repository.PostLikeRepository;

import com.glossy.evolchat.model.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostLikeService {
    @Autowired
    private PostLikeRepository postLikeRepository;

    public long countLikesByPostId(int postId) {
        return postLikeRepository.countByPostId(postId);
    }

    public List<Like> getAllLikes() {
        return postLikeRepository.findAll();
    }

    public Like getLikeById(int id) {
        return postLikeRepository.findById(id).orElse(null);
    }

    public Like saveLike(Like like) {
        return postLikeRepository.save(like);
    }

    public void deleteLike(int id) {
        postLikeRepository.deleteById(id);
    }
}
