package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPostPostId(int postId); // Post의 postId 필드를 기준으로 검색
}
