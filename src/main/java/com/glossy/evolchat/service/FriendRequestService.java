package com.glossy.evolchat.service;

import com.glossy.evolchat.model.Friend;
import com.glossy.evolchat.model.FriendRequest;
import com.glossy.evolchat.model.User;
import com.glossy.evolchat.repository.FriendRequestRepository;
import com.glossy.evolchat.repository.FriendRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final FriendRepository friendRepository;

    public FriendRequestService(FriendRequestRepository friendRequestRepository, FriendRepository friendRepository) {
        this.friendRequestRepository = friendRequestRepository;
        this.friendRepository = friendRepository;
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
        List<FriendRequest> receivedRequests = friendRequestRepository.findByReceiverAndIsAcceptedFalse(user);
        List<FriendRequest> sentRequests = friendRequestRepository.findBySenderAndIsAcceptedFalse(user);
        return Stream.concat(receivedRequests.stream(), sentRequests.stream())
                .collect(Collectors.toList());
    }

    public boolean cancelFriendRequest(Long requestId, User user) {
        FriendRequest request = friendRequestRepository.findById(requestId).orElse(null);
        if (request == null || !request.getSender().equals(user) && !request.getReceiver().equals(user)) {
            return false;
        }
        friendRequestRepository.deleteById(requestId);
        return true;
    }

    public List<FriendRequest> getSentRequests(User sender) {
        return friendRequestRepository.findBySenderAndIsAcceptedFalse(sender);
    }

    public boolean isAlreadyFriend(User sender, User receiver) {
        return friendRequestRepository.findBySenderAndIsAcceptedTrue(sender).stream()
                .anyMatch(request -> request.getReceiver().equals(receiver))
                || friendRequestRepository.findByReceiverAndIsAcceptedTrue(sender).stream()
                .anyMatch(request -> request.getSender().equals(receiver));
    }

    public boolean isRequestAlreadySent(User sender, User receiver) {
        return friendRequestRepository.findBySenderAndReceiver(sender, receiver) != null;
    }

    public boolean acceptFriendRequest(Long requestId, User user) {
        FriendRequest request = friendRequestRepository.findById(requestId).orElse(null);
        if (request == null || !request.getReceiver().equals(user)) {
            return false;
        }
        request.setAccepted(true);
        friendRequestRepository.save(request);

        // Add friends to the Friend table
        Friend friend = new Friend();
        friend.setUserId1(request.getSender().getId());
        friend.setUserId2(request.getReceiver().getId());
        friend.setBlocked(false); // Set to false initially
        friendRepository.save(friend);

        return true;
    }
}
