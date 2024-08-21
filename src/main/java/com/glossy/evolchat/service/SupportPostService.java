package com.glossy.evolchat.service;

import com.glossy.evolchat.model.SupportPost;
import com.glossy.evolchat.repository.SupportPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportPostService {

    private final SupportPostRepository supportPostRepository;

    public Page<SupportPost> getPosts(Pageable pageable, Integer boardId, Integer detailedCategory, String search) {
        if (boardId != null && detailedCategory != null && !search.isEmpty()) {
            return supportPostRepository.findByBoardIdAndDetailedCategoryAndTitleContainingIgnoreCase(boardId, detailedCategory, search, pageable);
        } else if (boardId != null && detailedCategory != null) {
            return supportPostRepository.findByBoardIdAndDetailedCategory(boardId, detailedCategory, pageable);
        } else if (boardId != null) {
            return supportPostRepository.findByBoardId(boardId, pageable);
        } else if (!search.isEmpty()) {
            return supportPostRepository.findByTitleContainingIgnoreCase(search, pageable);
        } else {
            return supportPostRepository.findAll(pageable);
        }
    }

    public Page<SupportPost> getPostsByBoardId(Integer boardId, Pageable pageable, String search) {
        if (search != null && !search.isEmpty()) {
            return supportPostRepository.findByBoardIdAndTitleContainingOrContentContaining(boardId, search, search, pageable);
        } else {
            return supportPostRepository.findByBoardId(boardId, pageable);
        }
    }

    public Page<SupportPost> getAllPosts(Pageable pageable, String search) {
        if (!search.isEmpty()) {
            return supportPostRepository.findByTitleContainingIgnoreCase(search, pageable);
        } else {
            return supportPostRepository.findAll(pageable);
        }
    }

}
