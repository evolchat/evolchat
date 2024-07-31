package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.SupportPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportPostRepository extends JpaRepository<SupportPost, Integer> {
    Page<SupportPost> findByBoardId(int boardId, Pageable pageable);
}
