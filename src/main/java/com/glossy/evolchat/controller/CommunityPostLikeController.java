package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.CommunityPostLike;
import com.glossy.evolchat.service.CommunityPostLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class CommunityPostLikeController {

    @Autowired
    private CommunityPostLikeService communityPostLikeService;

    @GetMapping
    public List<CommunityPostLike> getAllLikes() {
        return communityPostLikeService.getAllLikes();
    }

    @GetMapping("/{id}")
    public CommunityPostLike getLikeById(@PathVariable int id) {
        return communityPostLikeService.getLikeById(id);
    }

    @PostMapping
    public CommunityPostLike createLike(@RequestBody CommunityPostLike communityPostLike) {
        return communityPostLikeService.saveLike(communityPostLike);
    }

    @DeleteMapping("/{id}")
    public void deleteLike(@PathVariable int id) {
        communityPostLikeService.deleteLike(id);
    }
}
