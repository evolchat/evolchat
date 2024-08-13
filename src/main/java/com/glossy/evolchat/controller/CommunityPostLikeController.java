package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.CommunityPostLike;
import com.glossy.evolchat.service.CommunityPostLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/likes")
public class CommunityPostLikeController {

    @Autowired
    private CommunityPostLikeService communityPostLikeService;

    // 모든 좋아요 가져오기
    @GetMapping
    public List<CommunityPostLike> getAllLikes() {
        return communityPostLikeService.getAllLikes();
    }

    // 특정 ID의 좋아요 가져오기
    @GetMapping("/{id}")
    public CommunityPostLike getLikeById(@PathVariable int id) {
        return communityPostLikeService.getLikeById(id);
    }

    // 좋아요 생성
    @PostMapping
    public CommunityPostLike createLike(@RequestBody CommunityPostLike communityPostLike) {
        return communityPostLikeService.saveLike(communityPostLike);
    }

    // 좋아요 삭제
    @DeleteMapping("/{id}")
    public void deleteLike(@PathVariable int id) {
        communityPostLikeService.deleteLike(id);
    }

    // 좋아요 토글 (좋아요/좋아요 취소)
    @PostMapping("/toggle")
    public Map<String, Object> toggleLike(@RequestParam("postId") int postId,
                                          @AuthenticationPrincipal UserDetails currentUser) {
        Map<String, Object> response = new HashMap<>();
        boolean isLiked = communityPostLikeService.toggleLike(postId, currentUser.getUsername());

        response.put("success", true);
        response.put("isLiked", isLiked);
        response.put("likeCount", communityPostLikeService.getLikeCountByPostId(postId));

        return response;
    }
}