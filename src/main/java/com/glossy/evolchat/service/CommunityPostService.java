package com.glossy.evolchat.service;

import com.glossy.evolchat.model.CommunityPost;
import com.glossy.evolchat.repository.CommunityPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Long getTotalPages(int size) {
        long totalCount = communityPostRepository.count(); // 전체 포스트 수 조회
        return (totalCount + size - 1) / size; // 총 페이지 수 계산
    }

    public CommunityPost getPostById(Integer id) {
        return communityPostRepository.findById(id).orElse(null); // ID로 포스트 조회
    }
}
