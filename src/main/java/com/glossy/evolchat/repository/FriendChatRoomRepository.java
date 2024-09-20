package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.FriendChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendChatRoomRepository extends JpaRepository<FriendChatRoom, Integer> {
    // 특정 두 사용자 간의 채팅방을 찾는 메서드
    @Query("SELECT f FROM FriendChatRoom f WHERE (f.user1Id = :user1Id AND f.user2Id = :user2Id) OR (f.user1Id = :user2Id AND f.user2Id = :user1Id)")
    Optional<FriendChatRoom> findByUserIds(@Param("user1Id") int user1Id, @Param("user2Id") int user2Id);

    // 특정 사용자와 연결된 모든 채팅방을 찾는 메서드 (User1 또는 User2로)
    List<FriendChatRoom> findByUser1IdOrUser2Id(int userId1, int userId2);
}
