package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.CommunityComment;
import com.glossy.evolchat.model.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Integer> {
    List<CommunityComment> findByCommunityPost(CommunityPost communityPost);
}
