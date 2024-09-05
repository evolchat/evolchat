package com.glossy.evolchat.service;

import com.glossy.evolchat.model.BettingPointsHistory;
import com.glossy.evolchat.repository.BettingPointsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BettingPointsHistoryService {

    @Autowired
    private BettingPointsHistoryRepository repository;

    public void save(BettingPointsHistory history) {
        repository.save(history);
    }

    public void saveBettingPointsHistory(String username, int pointsUsed, int remainingPoints, String activityType, String itemName, int quantity) {
        BettingPointsHistory history = new BettingPointsHistory();
        history.setUsername(username);
        history.setPointsChange(-pointsUsed);
        history.setRemainingPoint(remainingPoints);
        history.setActivityType(activityType);
        history.setContent("아이템 구매: " + itemName + " x " + quantity);
        history.setCreatedAt(LocalDateTime.now());

        save(history);
    }
}