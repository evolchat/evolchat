package com.glossy.evolchat.service;

import com.glossy.evolchat.model.Like;
import com.glossy.evolchat.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    public List<Like> getAllLikes() {
        return likeRepository.findAll();
    }

    public Like getLikeById(int id) {
        return likeRepository.findById(id).orElse(null);
    }

    public Like saveLike(Like like) {
        return likeRepository.save(like);
    }

    public void deleteLike(int id) {
        likeRepository.deleteById(id);
    }
}
