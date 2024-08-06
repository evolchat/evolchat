package com.glossy.evolchat.controller;

import com.glossy.evolchat.dto.CommunityPostDto;
import com.glossy.evolchat.model.CommunityPost;
import com.glossy.evolchat.service.CommunityPostLikeService;
import com.glossy.evolchat.service.CommunityPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/community-posts")
@RequiredArgsConstructor
public class CommunityPostController {

    private final CommunityPostService communityPostService;
    private final CommunityPostLikeService communityPostLikeService;

    @GetMapping
    public ResponseEntity<List<CommunityPostDto>> getCommunityPosts(@RequestParam(defaultValue = "1") int page,
                                                           @RequestParam(defaultValue = "20") int size,
                                                           @RequestParam(required = false) Integer boardId) {
        Pageable pageable = PageRequest.of(page - 1, size); // 페이지 번호를 0부터 시작하도록 수정
        Page<CommunityPost> postsPage;

        if (boardId != null) {
            postsPage = communityPostService.getPostsByBoardId(boardId, pageable);
        } else {
            postsPage = communityPostService.getAllPosts(pageable);
        }

        List<CommunityPostDto> communityPostDtos = postsPage.getContent().stream().map(this::convertToDto).collect(Collectors.toList());

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Pages", String.valueOf(postsPage.getTotalPages())); // 전체 페이지 수를 HTTP 헤더에 추가

        return new ResponseEntity<>(communityPostDtos, headers, HttpStatus.OK);
    }

    private CommunityPostDto convertToDto(CommunityPost communityPost) {
        long likeCount = communityPostLikeService.countLikesByPostId(communityPost.getPostId());
        CommunityPostDto communityPostDto = new CommunityPostDto();
        communityPostDto.setPostId(communityPost.getPostId());
        communityPostDto.setUserId(communityPost.getUserId());
        communityPostDto.setBoardId(communityPost.getBoardId());
        communityPostDto.setTitle(communityPost.getTitle());
        communityPostDto.setContent(communityPost.getContent());
        communityPostDto.setLikeCount(likeCount);
        communityPostDto.setViews(communityPost.getViews());
        communityPostDto.setCreatedAt(communityPost.getCreatedAt().toString());
        return communityPostDto;
    }
}
