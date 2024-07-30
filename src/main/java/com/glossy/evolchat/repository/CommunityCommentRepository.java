package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.CommunityComment;
import com.glossy.evolchat.model.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Integer> {
    List<CommunityComment> findByCommunityPost(CommunityPost post);
}
