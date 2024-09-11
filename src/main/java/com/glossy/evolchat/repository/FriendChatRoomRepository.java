package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.FriendChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendChatRoomRepository extends JpaRepository<FriendChatRoom, Integer> {
    // 특정 두 사용자 간의 채팅방을 찾는 메서드
    Optional<FriendChatRoom> findByUser1IdAndUser2Id(int user1Id, int user2Id);

    // 특정 사용자와 연결된 모든 채팅방을 찾는 메서드 (User1으로)
    List<FriendChatRoom> findByUser1Id(int userId);

    // 특정 사용자와 연결된 모든 채팅방을 찾는 메서드 (User2로)
    List<FriendChatRoom> findByUser2Id(int userId);

    // 특정 사용자와 연결된 모든 채팅방을 찾는 메서드 (User1 또는 User2로)
    List<FriendChatRoom> findByUser1IdOrUser2Id(int userId1, int userId2);
}
