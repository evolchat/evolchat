package com.glossy.evolchat.controller;

import com.glossy.evolchat.dto.PostDto;
import com.glossy.evolchat.model.Post;
import com.glossy.evolchat.service.LikeService;
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
    private final LikeService likeService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(@RequestParam(defaultValue = "1") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page - 1, size); // 페이지 번호를 0부터 시작하도록 수정
        Page<Post> postsPage = postService.getAllPosts(pageable);
        List<PostDto> postDtos = postsPage.getContent().stream().map(this::convertToDto).collect(Collectors.toList());

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Pages", String.valueOf(postsPage.getTotalPages())); // 전체 페이지 수를 HTTP 헤더에 추가

        return new ResponseEntity<>(postDtos, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Integer id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        Post post = postService.getPostById(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        PostDto postDto = convertToDto(post);
        return ResponseEntity.ok(postDto);
    }

    private PostDto convertToDto(Post post) {
        long likeCount = likeService.countLikesByPostId(post.getPostId());
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