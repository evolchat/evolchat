package com.glossy.evolchat.service;

import com.glossy.evolchat.model.GoldChipPointsHistory;
import com.glossy.evolchat.repository.GoldChipPointsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GoldChipPointsHistoryService {

    @Autowired
    private GoldChipPointsHistoryRepository repository;

    public void save(GoldChipPointsHistory history) {
        repository.save(history);
    }

    public void saveGoldChipPointsHistory(String username, int pointsUsed, int remainingPoints, String activityType, String itemName, String target, int quantity, Integer usedChip, String exchangedAmount) {
        GoldChipPointsHistory history = new GoldChipPointsHistory();
        history.setActivityType(activityType);
        history.setTarget(target); // 대상이 있을 경우 설정
        history.setQuantity(quantity);
        history.setRemainingQuantity(remainingPoints);
        history.setUsedChip(usedChip); // 구매 시 사용 캐시 설정
        history.setExchangedAmount(exchangedAmount); // 환전 금액 설정
        history.setCreatedAt(LocalDateTime.now());

        save(history);
    }
}
