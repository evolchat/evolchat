package com.glossy.evolchat.controller;

import com.glossy.evolchat.dto.PostDto;
import com.glossy.evolchat.model.Post;
import com.glossy.evolchat.service.PostLikeService;
import com.glossy.evolchat.service.PostService;
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
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostLikeService postLikeService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts(@RequestParam(defaultValue = "1") int page,
                                                     @RequestParam(defaultValue = "20") int size,
                                                     @RequestParam(required = false) Integer boardId) {
        Pageable pageable = PageRequest.of(page - 1, size); // 페이지 번호를 0부터 시작하도록 수정
        Page<Post> postsPage;

        if (boardId != null) {
            postsPage = postService.getPostsByBoardId(boardId, pageable);
        } else {
            postsPage = postService.getAllPosts(pageable);
        }

        List<PostDto> postDtos = postsPage.getContent().stream().map(this::convertToDto).collect(Collectors.toList());

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Pages", String.valueOf(postsPage.getTotalPages())); // 전체 페이지 수를 HTTP 헤더에 추가

        return new ResponseEntity<>(postDtos, headers, HttpStatus.OK);
    }

    private PostDto convertToDto(Post post) {
        long likeCount = postLikeService.countLikesByPostId(post.getPostId());
        PostDto postDto = new PostDto();
        postDto.setPostId(post.getPostId());
        postDto.setUserId(post.getUserId());
        postDto.setBoardId(post.getBoardId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setLikeCount(likeCount);
        postDto.setViews(post.getViews());
        postDto.setCreatedAt(post.getCreatedAt().toString());
        return postDto;
    }
}
