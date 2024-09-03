package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.BlockedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockedUserRepository extends JpaRepository<BlockedUser, Integer> {

    List<BlockedUser> findByBlockerId(int blockerId);

    boolean existsByBlockerIdAndBlockedId(int blockerId, int blockedId);

    void deleteByBlockerIdAndBlockedId(int blockerId, int blockedId);
}
