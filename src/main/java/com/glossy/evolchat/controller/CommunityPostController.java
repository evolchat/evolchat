package com.glossy.evolchat.controller;

import com.glossy.evolchat.dto.CommunityPostDto;
import com.glossy.evolchat.model.CommunityComment;
import com.glossy.evolchat.model.CommunityPost;
import com.glossy.evolchat.repository.CommunityPostRepository;
import com.glossy.evolchat.service.CommunityCommentService;
import com.glossy.evolchat.service.CommunityPostLikeService;
import com.glossy.evolchat.service.CommunityPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/community-posts")
@RequiredArgsConstructor
public class CommunityPostController {

    @Autowired
    private CommunityPostRepository communityPostRepository;

    @Autowired
    private CommunityPostService communityPostService;

    @Autowired
    private CommunityPostLikeService communityPostLikeService;

    @Autowired
    private CommunityCommentService communityCommentService;

    @GetMapping
    public ResponseEntity<List<CommunityPostDto>> getCommunityPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Integer boardId,
            @RequestParam(defaultValue = "latest") String sort,
            @RequestParam(required = false) String search) {

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("createdAt")));

        Page<CommunityPost> postsPage;

        if (boardId != null) {
            if (search != null && !search.isEmpty()) {
                // 제목, 내용, 태그에서 검색
                postsPage = communityPostService.getPostsByBoardIdAndSearch(boardId, search, pageable);
            } else {
                postsPage = communityPostService.getPostsByBoardId(boardId, pageable);
            }
        } else {
            if (search != null && !search.isEmpty()) {
                // 전체 게시판에서 검색
                postsPage = communityPostService.getAllPostsBySearch(search, pageable);
            } else {
                postsPage = communityPostService.getAllPosts(pageable);
            }
        }

        List<CommunityPostDto> communityPostDtos = postsPage.getContent().stream().map(post -> {
            int likeCount = communityPostLikeService.getLikeCountByPostId(post.getPostId());
            List<CommunityComment> comments = communityCommentService.getCommentsByPost(post);
            int commentCount = comments.size();
            return convertToDto(post, likeCount, commentCount);
        }).collect(Collectors.toList());

        switch (sort) {
            case "popular":
                communityPostDtos.sort((p1, p2) -> Integer.compare((int) p2.getLikeCount(), (int) p1.getLikeCount()));
                break;
            case "most-comments":
                communityPostDtos.sort((p1, p2) -> Integer.compare((int) p2.getCommentCount(), (int) p1.getCommentCount()));
                break;
            case "latest":
            default:
                communityPostDtos.sort((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()));
                break;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Pages", String.valueOf(postsPage.getTotalPages()));

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

    private CommunityPostDto convertToDto(CommunityPost communityPost, int likeCount, int commentCount) {
        CommunityPostDto communityPostDto = new CommunityPostDto();
        communityPostDto.setPostId(communityPost.getPostId());
        communityPostDto.setUserId(communityPost.getUserId());
        communityPostDto.setBoardId(communityPost.getBoardId());
        communityPostDto.setTitle(communityPost.getTitle());

        // Remove images and truncate text to 100 characters
        String truncatedContent = truncateContent(communityPost.getContent());
        communityPostDto.setContent(truncatedContent);

        communityPostDto.setLikeCount(likeCount);
        communityPostDto.setCommentCount(commentCount);
        communityPostDto.setViews(communityPost.getViews());
        communityPostDto.setCreatedAt(communityPost.getCreatedAt().toString());

        // 첫 번째 Base64 이미지를 추출하여 imageUrl에 설정
        String imageUrl = extractFirstBase64Image(communityPost.getContent());
        if (imageUrl != null) {
            communityPostDto.setImageUrl(imageUrl);
        } else {
            // 기본 이미지 설정
            communityPostDto.setImageUrl("../../static/images/svg/logo.svg");
        }

        return communityPostDto;
    }

    private String extractFirstBase64Image(String content) {
        // Base64 이미지 URL 추출을 위한 정규식 패턴
        String base64ImagePattern = "<img\\s+[^>]*src\\s*=\\s*\"(data:image/[^;]+;base64,[^\"]+)\"[^>]*>";
        Pattern pattern = Pattern.compile(base64ImagePattern);
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            return matcher.group(1); // 첫 번째 Base64 이미지 반환
        }

        return null;
    }

    private String truncateContent(String content) {
        Document document = Jsoup.parse(content);
        // Remove all images
        document.select("img").remove();
        // Get the text content and truncate it to 100 characters
        String text = document.text();
        if (text.length() > 100) {
            return text.substring(0, 100) + "...";
        }
        return text;
    }
}