package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.Like;
import com.glossy.evolchat.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @GetMapping
    public List<Like> getAllLikes() {
        return likeService.getAllLikes();
    }

    @GetMapping("/{id}")
    public Like getLikeById(@PathVariable int id) {
        return likeService.getLikeById(id);
    }

    @PostMapping
    public Like createLike(@RequestBody Like like) {
        return likeService.saveLike(like);
    }

    @DeleteMapping("/{id}")
    public void deleteLike(@PathVariable int id) {
        likeService.deleteLike(id);
    }
}
