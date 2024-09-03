package com.glossy.evolchat.service;

import com.glossy.evolchat.model.BlockedUser;
import com.glossy.evolchat.repository.BlockedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockedUserService {

    @Autowired
    private BlockedUserRepository blockedUserRepository;

    public void blockUser(int blockerId, int blockedId) {
        if (!blockedUserRepository.existsByBlockerIdAndBlockedId(blockerId, blockedId)) {
            BlockedUser blockedUser = new BlockedUser();
            blockedUser.setBlockerId(blockerId);
            blockedUser.setBlockedId(blockedId);
            blockedUserRepository.save(blockedUser);
        }
    }

    public List<BlockedUser> getBlockedUsers(int blockerId) {
        return blockedUserRepository.findByBlockerId(blockerId);
    }

    public boolean unblockUser(int blockerId, int blockedId) {
        if (blockedUserRepository.existsByBlockerIdAndBlockedId(blockerId, blockedId)) {
            blockedUserRepository.deleteByBlockerIdAndBlockedId(blockerId, blockedId);
            return true;
        }
        return false;
    }
}
