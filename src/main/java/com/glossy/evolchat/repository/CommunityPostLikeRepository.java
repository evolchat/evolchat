package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.CommunityPostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityPostLikeRepository extends JpaRepository<CommunityPostLike, Integer> {
    long countByPostId(int postId);
    Optional<CommunityPostLike> findByPostIdAndUserId(int postId, String userId);

}
