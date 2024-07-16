package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.Like;
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
    public List<Like> getAllLikes() {
        return postLikeService.getAllLikes();
    }

    @GetMapping("/{id}")
    public Like getLikeById(@PathVariable int id) {
        return postLikeService.getLikeById(id);
    }

    @PostMapping
    public Like createLike(@RequestBody Like like) {
        return postLikeService.saveLike(like);
    }

    @DeleteMapping("/{id}")
    public void deleteLike(@PathVariable int id) {
        postLikeService.deleteLike(id);
    }
}
