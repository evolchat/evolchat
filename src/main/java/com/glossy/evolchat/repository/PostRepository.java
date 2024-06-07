package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
