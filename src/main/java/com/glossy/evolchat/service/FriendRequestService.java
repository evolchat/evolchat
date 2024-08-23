package com.glossy.evolchat.service;

import com.glossy.evolchat.model.FriendRequest;
import com.glossy.evolchat.model.User;
import com.glossy.evolchat.repository.FriendRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;

    public FriendRequestService(FriendRequestRepository friendRequestRepository) {
        this.friendRequestRepository = friendRequestRepository;
    }

    public void sendFriendRequest(User sender, User receiver) {
        if (receiver == null) {
            throw new IllegalArgumentException("Receiver cannot be null");
        }
        FriendRequest existingRequest = friendRequestRepository.findBySenderAndReceiver(sender, receiver);
        if (existingRequest != null) {
            return;
        }
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        friendRequestRepository.save(friendRequest);
    }
    public List<FriendRequest> getPendingRequests(User user) {
        return friendRequestRepository.findBySender(user);
    }

    public void cancelFriendRequest(Long requestId) {
        friendRequestRepository.deleteById(requestId);
    }

    // 사용자가 보낸 친구 요청 중 수락되지 않은 요청을 가져옴
    public List<FriendRequest> getSentRequests(User sender) {
        return friendRequestRepository.findBySenderAndIsAcceptedFalse(sender);
    }
}
