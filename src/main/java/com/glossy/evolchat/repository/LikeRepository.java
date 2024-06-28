package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    @Query("SELECT COUNT(l) FROM Like l WHERE l.postId = :postId")
    long countByPostId(@Param("postId") int postId);
}
