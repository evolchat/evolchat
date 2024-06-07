package com.glossy.evolchat.service;

import com.glossy.evolchat.model.UserPoints;
import com.glossy.evolchat.repository.UserPointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserPointsService {

    @Autowired
    private UserPointsRepository userPointsRepository;

    public List<UserPoints> getAllUserPoints() {
        return userPointsRepository.findAll();
    }

    public UserPoints getUserPointsById(int id) {
        return userPointsRepository.findById(id).orElse(null);
    }

    public UserPoints saveUserPoints(UserPoints userPoints) {
        return userPointsRepository.save(userPoints);
    }

    public void deleteUserPoints(int id) {
        userPointsRepository.deleteById(id);
    }
}
