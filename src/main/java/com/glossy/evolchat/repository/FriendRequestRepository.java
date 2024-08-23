package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.FriendRequest;
import com.glossy.evolchat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findBySender(User sender);
    List<FriendRequest> findByReceiver(User receiver);
    FriendRequest findBySenderAndReceiver(User sender, User receiver);

    List<FriendRequest> findBySenderAndIsAcceptedFalse(User sender);
}
