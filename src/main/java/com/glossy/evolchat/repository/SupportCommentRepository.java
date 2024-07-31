package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.SupportComment;
import com.glossy.evolchat.model.SupportPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportCommentRepository extends JpaRepository<SupportComment, Integer> {
    List<SupportComment> findBySupportPost(SupportPost post);
}
