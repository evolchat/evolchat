package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Integer> { // id 타입을 Integer로 변경

    // 기존 친구 목록 조회
    List<Friend> findByUserId1OrUserId2(int userId1, int userId2);

    List<Friend> findByUserId1AndUserId2(int userId1, int userId2);

    // Long 타입으로 친구 목록 조회
    @Query("SELECT f FROM Friend f WHERE f.userId1 = :userId OR f.userId2 = :userId")
    List<Friend> findFriendsByUserId(int userId);

    // 차단 목록 조회
    @Query("SELECT f FROM Friend f WHERE f.isBlocked = true AND (f.userId1 = :userId OR f.userId2 = :userId)")
    List<Friend> findBlockedFriendsByUserId(int userId);

    // 차단 해제
    @Modifying
    @Query("UPDATE Friend f SET f.isBlocked = false WHERE (f.userId1 = :userId AND f.userId2 = :friendId) OR (f.userId1 = :friendId AND f.userId2 = :userId)")
    int unblockUser(int userId, Integer friendId);
}
