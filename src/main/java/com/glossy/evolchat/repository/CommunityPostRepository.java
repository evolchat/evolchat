package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.CommunityPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Integer> {
    Page<CommunityPost> findByBoardId(int boardId, Pageable pageable);
}
