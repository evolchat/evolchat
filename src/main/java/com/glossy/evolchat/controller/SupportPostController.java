package com.glossy.evolchat.controller;

import com.glossy.evolchat.dto.SupportPostDto;
import com.glossy.evolchat.model.SupportPost;
import com.glossy.evolchat.service.SupportPostService;
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
@RequestMapping("/support-posts")
@RequiredArgsConstructor
public class SupportPostController {

    private final SupportPostService supportPostService;

    @GetMapping
    public ResponseEntity<List<SupportPostDto>> getSupportPosts(@RequestParam(defaultValue = "1") Integer page,
                                                                @RequestParam(defaultValue = "20") Integer size,
                                                                @RequestParam(required = false) Integer boardId,
                                                                @RequestParam(required = false) Integer detailedCategory,
                                                                @RequestParam(required = false, defaultValue = "") String search) {
        Pageable pageable = PageRequest.of(page - 1, size); // 페이지 번호를 0부터 시작하도록 수정
        Page<SupportPost> postsPage;

        // 게시물 조회
        if (boardId != null) {
            postsPage = supportPostService.getPosts(pageable, boardId, detailedCategory, search);
        } else {
            postsPage = supportPostService.getAllPosts(pageable, search);
        }

        List<SupportPostDto> supportPostDtos = postsPage.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Pages", String.valueOf(postsPage.getTotalPages())); // 전체 페이지 수를 HTTP 헤더에 추가

        return new ResponseEntity<>(supportPostDtos, headers, HttpStatus.OK);
    }

    private SupportPostDto convertToDto(SupportPost supportPost) {
        SupportPostDto supportPostDto = new SupportPostDto();
        supportPostDto.setPostId(supportPost.getPostId());
        supportPostDto.setUserId(supportPost.getUserId());
        supportPostDto.setBoardId(supportPost.getBoardId());
        supportPostDto.setTitle(supportPost.getTitle());
        supportPostDto.setContent(supportPost.getContent());
        supportPostDto.setViews(supportPost.getViews());
        supportPostDto.setCreatedAt(supportPost.getCreatedAt().toString());
        supportPostDto.setDetailedCategory(supportPost.getDetailedCategory()); // 상세 분류 추가
        return supportPostDto;
    }
}