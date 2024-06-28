package com.glossy.evolchat.service;

import com.glossy.evolchat.model.Post;
import com.glossy.evolchat.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Page<Post> getPostsByBoardId(int boardId, Pageable pageable) {
        return postRepository.findByBoardId(boardId, pageable);
    }

    public Long getTotalPages(int size) {
        long totalCount = postRepository.count(); // 전체 포스트 수 조회
        return (totalCount + size - 1) / size; // 총 페이지 수 계산
    }

    public Post getPostById(Integer id) {
        return postRepository.findById(id).orElse(null); // ID로 포스트 조회
    }
}
