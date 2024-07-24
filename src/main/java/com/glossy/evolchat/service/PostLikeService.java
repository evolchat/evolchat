package com.glossy.evolchat.service;

import com.glossy.evolchat.model.PostLike;
import com.glossy.evolchat.repository.PostLikeRepository;

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

    public List<PostLike> getAllLikes() {
        return postLikeRepository.findAll();
    }

    public PostLike getLikeById(int id) {
        return postLikeRepository.findById(id).orElse(null);
    }

    public PostLike saveLike(PostLike postLike) {
        return postLikeRepository.save(postLike);
    }

    public void deleteLike(int id) {
        postLikeRepository.deleteById(id);
    }

}
