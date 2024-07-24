package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.PostLike;
import com.glossy.evolchat.service.PostLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class PostLikeController {

    @Autowired
    private PostLikeService postLikeService;

    @GetMapping
    public List<PostLike> getAllLikes() {
        return postLikeService.getAllLikes();
    }

    @GetMapping("/{id}")
    public PostLike getLikeById(@PathVariable int id) {
        return postLikeService.getLikeById(id);
    }

    @PostMapping
    public PostLike createLike(@RequestBody PostLike postLike) {
        return postLikeService.saveLike(postLike);
    }

    @DeleteMapping("/{id}")
    public void deleteLike(@PathVariable int id) {
        postLikeService.deleteLike(id);
    }
}
