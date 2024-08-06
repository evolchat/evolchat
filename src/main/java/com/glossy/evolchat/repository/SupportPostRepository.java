package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.SupportPost;
import com.glossy.evolchat.model.SupportPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SupportPostRepository extends JpaRepository<SupportPost, Integer> {
    Page<SupportPost> findByBoardId(Integer boardId, Pageable pageable);

    Page<SupportPost> findByBoardIdAndDetailedCategory(Integer boardId, Integer detailedCategory, Pageable pageable);

    Page<SupportPost> findByBoardIdAndDetailedCategoryAndTitleContainingIgnoreCase(Integer boardId, Integer detailedCategory, String title, Pageable pageable);

    Page<SupportPost> findByTitleContainingIgnoreCase(String title, Pageable pageable);

}
