package com.glossy.evolchat.service;

import com.glossy.evolchat.model.Friend;
import com.glossy.evolchat.model.User;
import com.glossy.evolchat.repository.FriendRepository;
import com.glossy.evolchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    public void addFriend(int userId1, int userId2) {
        Friend friend = new Friend();
        friend.setUserId1(userId1);
        friend.setUserId2(userId2);
        friendRepository.save(friend);
    }

    // 메소드 오버로딩 처리
    public List<Friend> getId1OrId2Friends(int userId) {
        return friendRepository.findByUserId1OrUserId2(userId, userId);
    }

    public List<Friend> getFriends(Integer userId) {
        return friendRepository.findFriendsByUserId(userId);
    }


    public List<Friend> getBlocklist(Integer userId) {
        return friendRepository.findBlockedFriendsByUserId(userId);
    }

    public void blockUser(Integer userId1, Integer userId2) {
        Friend friend = new Friend();
        friend.setUserId1(userId1);
        friend.setUserId2(userId2);
        friend.setBlocked(true); // 차단 상태를 true로 설정
        friendRepository.save(friend);
    }

    public void unblockUser(Integer userId1, Integer userId2) {
        friendRepository.unblockUser(userId1, userId2);
    }

}
