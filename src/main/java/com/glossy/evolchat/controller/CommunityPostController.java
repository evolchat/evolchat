package com.glossy.evolchat.controller;

import com.glossy.evolchat.dto.CommunityPostDto;
import com.glossy.evolchat.model.CommunityPost;
import com.glossy.evolchat.repository.CommunityPostRepository;
import com.glossy.evolchat.service.CommunityPostLikeService;
import com.glossy.evolchat.service.CommunityPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/community-posts")
@RequiredArgsConstructor
public class CommunityPostController {

    @Autowired
    private CommunityPostRepository communityPostRepository;
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

    @PostMapping("/submit_post")
    @ResponseBody
    public ResponseEntity<?> submitPost(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("tags") String tags,
            @RequestParam("boardId") int boardId,
            @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();

        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 세션이 만료되었습니다.");
        }

        CommunityPost communityPost = new CommunityPost();
        communityPost.setTitle(title);
        communityPost.setContent(content);
        communityPost.setBoardId(boardId);
        communityPost.setTags(tags);
        communityPost.setUserId(username);
        communityPost.setCreatedAt(LocalDateTime.now());
        communityPost.setUpdatedAt(LocalDateTime.now());

        communityPostRepository.save(communityPost);

        return ResponseEntity.ok(communityPost);
    }

    private CommunityPostDto convertToDto(CommunityPost communityPost) {
        int likeCount = communityPostLikeService.getLikeCountByPostId(communityPost.getPostId());
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
