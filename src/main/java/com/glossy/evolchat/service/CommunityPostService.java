package com.glossy.evolchat.service;

import com.glossy.evolchat.model.CommunityPost;
import com.glossy.evolchat.repository.CommunityPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;  // 추가된 import
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityPostService {

    private final CommunityPostRepository communityPostRepository;

    public Page<CommunityPost> getAllPosts(Pageable pageable) {
        return communityPostRepository.findAll(pageable);
    }

    public Page<CommunityPost> getPostsByBoardId(int boardId, Pageable pageable) {
        return communityPostRepository.findByBoardId(boardId, pageable);
    }

    public Page<CommunityPost> getPosts(int page, int size, int boardId, String search) {
        Pageable pageable = PageRequest.of(page - 1, size);  // 수정된 부분
        List<CommunityPost> posts = communityPostRepository.findByBoardIdAndSearch(boardId, search);
        return new PageImpl<>(posts, pageable, posts.size());
    }

    public Long getTotalPages(int size) {
        long totalCount = communityPostRepository.count(); // 전체 포스트 수 조회
        return (totalCount + size - 1) / size; // 총 페이지 수 계산
    }

    public CommunityPost getPostById(Integer id) {
        return communityPostRepository.findById(id).orElse(null); // ID로 포스트 조회
    }
}
