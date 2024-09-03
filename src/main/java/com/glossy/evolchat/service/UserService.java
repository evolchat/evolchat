package com.glossy.evolchat.service;

import com.glossy.evolchat.model.Friend;
import com.glossy.evolchat.model.User;
import com.glossy.evolchat.model.UserPoints;
import com.glossy.evolchat.repository.FriendRepository;
import com.glossy.evolchat.repository.UserPointsRepository;
import com.glossy.evolchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPointsRepository userPointsRepository;

    @Autowired
    private FriendRepository friendRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) { // id 타입을 Integer로 변경
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) { // id 타입을 Integer로 변경
        userRepository.deleteById(id);
    }

    public Page<User> getRankedUsers(Pageable pageable) {
        return userRepository.findAllByOrderByBettingProfitDesc(pageable);
    }

    public Optional<UserPoints> getUserPointsByUsername(String username) {
        return userPointsRepository.findByUsername(username);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname).orElse(null);
    }

    public User getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return getUserByUsername(username);
        }
        return null;
    }

    // Friend related methods

    public void sendFriendRequest(User sender, User receiver) {
        if (!isFriends(sender.getId(), receiver.getId())) {
            Friend friendRequest = new Friend();
            friendRequest.setUserId1(sender.getId());
            friendRequest.setUserId2(receiver.getId());
            friendRepository.save(friendRequest);
        }
    }

    public void removeFriend(User user1, User user2) {
        List<Friend> friends = friendRepository.findByUserId1AndUserId2(user1.getId(), user2.getId());
        if (!friends.isEmpty()) {
            for (Friend friend : friends) {
                friendRepository.delete(friend);
            }
        }
    }

    public List<Friend> getFriends(User user) {
        return friendRepository.findByUserId1OrUserId2(user.getId(), user.getId());
    }

    public boolean isFriends(Integer userId1, Integer userId2) {
        List<Friend> friends = friendRepository.findByUserId1AndUserId2(userId1, userId2);
        return !friends.isEmpty() || !friendRepository.findByUserId1AndUserId2(userId2, userId1).isEmpty();
    }
    public void updateCurrentPage(Integer userId, String currentPage) { // id 타입을 Integer로 변경
        userRepository.findById(userId).ifPresent(user -> {
            user.setCurrentPage(currentPage);
            userRepository.save(user);
        });
    }
}
