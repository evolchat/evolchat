package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Page<Post> findByBoardId(int boardId, Pageable pageable);
}
