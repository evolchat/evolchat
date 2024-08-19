package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.CommunityPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Integer> {
    @Query("SELECT p FROM CommunityPost p WHERE p.boardId = :boardId AND (p.title LIKE %:search% OR p.content LIKE %:search% OR p.tags LIKE %:search%) AND p.isDeleted = false")
    List<CommunityPost> findByBoardIdAndSearch(@Param("boardId") int boardId, @Param("search") String search);
    Page<CommunityPost> findByBoardId(int boardId, Pageable pageable);

    @Query("SELECT p FROM CommunityPost p WHERE p.boardId = :boardId AND " +
            "(p.title LIKE %:search% OR p.content LIKE %:search% OR p.tags LIKE %:search%)")
    Page<CommunityPost> findByBoardIdAndTitleContainingOrContentContainingOrTagsContaining(@Param("boardId") int boardId, @Param("search") String search, Pageable pageable);


    Page<CommunityPost> findByTitleContainingOrContentContainingOrTagsContaining(
            String title, String content, String tags, Pageable pageable);
}
