package com.glossy.evolchat.service;

import com.glossy.evolchat.model.UserPoints;
import com.glossy.evolchat.repository.UserPointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPointsService {

    @Autowired
    private UserPointsRepository userPointsRepository;

    public Optional<UserPoints> getUserPointsById(int id) {
        return userPointsRepository.findById(id);
    }

    public UserPoints saveUserPoints(UserPoints userPoints) {
        return userPointsRepository.save(userPoints);
    }

    public UserPoints initializeUserPoints(String username) {
        // 초기 포인트 설정 (원하는 기본값으로 설정할 수 있습니다)
        UserPoints userPoints = new UserPoints();
        userPoints.setUsername(username);
        userPoints.setCCash(0);  // 예시: C 캐시 초기값
        userPoints.setBBettingPoints(0);  // 예시: B 베팅 포인트 초기값
        userPoints.setAActivityPoints(0);  // 예시: A 활동 포인트 초기값
        userPoints.setGGoldChip(0);  // 예시: G 골드 칩 초기값

        return userPointsRepository.save(userPoints);
    }

}
