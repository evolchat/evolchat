package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    long countByPostId(int postId);
}
