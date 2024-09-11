package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.FriendRequest;
import com.glossy.evolchat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    List<FriendRequest> findBySender(User sender); // 요청을 보낸 사용자로 조회

    // 발신자와 수신자로 친구 요청 조회 (중복 요청 방지용)
    FriendRequest findBySenderAndReceiver(User sender, User receiver);

    // 수락되지 않은 친구 요청 중 발신자가 특정 사용자일 때 조회
    List<FriendRequest> findBySenderAndIsAcceptedFalse(User sender);

    // 수락되지 않은 친구 요청 중 수신자가 특정 사용자일 때 조회
    List<FriendRequest> findByReceiverAndIsAcceptedFalse(User receiver);

    // 수락된 친구 요청 중 발신자 조회
    List<FriendRequest> findBySenderAndIsAcceptedTrue(User sender);

    // 수락된 친구 요청 중 수신자 조회
    List<FriendRequest> findByReceiverAndIsAcceptedTrue(User receiver);
}
