package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {
    long countByPostId(int postId);
    Optional<PostLike> findByPostIdAndUserId(int postId, String userId);
}
