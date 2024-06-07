package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
