package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.FriendChatMessage;
import com.glossy.evolchat.model.FriendChatRoom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendChatMessageRepository extends JpaRepository<FriendChatMessage, Integer> {

    // FriendChatRoom 객체를 기반으로 메시지를 검색
//    List<FriendChatMessage> findByChatRoom(FriendChatRoom chatRoom);

    // 채팅방 ID로 메시지들을 시간 순서대로 정렬하여 가져오는 메서드
    @Query("SELECT m FROM FriendChatMessage m WHERE m.chatRoom.id = :chatRoomId ORDER BY m.timestamp ASC")
    List<FriendChatMessage> findByChatRoomIdOrderByTimestampAsc(@Param("chatRoomId") int chatRoomId);

    // 채팅방 ID로 최근 메시지를 특정 개수만큼 가져오는 메서드
    @Query("SELECT m FROM FriendChatMessage m WHERE m.chatRoom.id = :chatRoomId ORDER BY m.timestamp DESC")
    List<FriendChatMessage> findTopNByChatRoomIdOrderByTimestampDesc(@Param("chatRoomId") int chatRoomId, Pageable pageable);

    @Query("SELECT m FROM FriendChatMessage m WHERE m.chatRoom.id = :chatRoomId AND m.senderId <> :userId AND m.readStatus = false")
    List<FriendChatMessage> findUnreadMessages(@Param("userId") int userId, @Param("chatRoomId") int chatRoomId);

    @Query("SELECT COUNT(m) FROM FriendChatMessage m WHERE m.chatRoom.id = :chatRoomId AND m.senderId <> :userId AND m.readStatus = false")
    int countUnreadMessages(@Param("userId") int userId, @Param("chatRoomId") int chatRoomId);
}