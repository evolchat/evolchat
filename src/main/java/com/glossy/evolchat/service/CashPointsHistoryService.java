package com.glossy.evolchat.service;

import com.glossy.evolchat.model.CashPointsHistory;
import com.glossy.evolchat.repository.CashPointsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CashPointsHistoryService {

    @Autowired
    private CashPointsHistoryRepository repository;

    public void save(CashPointsHistory history) {
        repository.save(history);
    }

    public void saveCashPointsHistory(String username, int pointsUsed, int remainingPoints, String activityType, String itemName, int quantity) {
        CashPointsHistory cashPointsHistory = new CashPointsHistory();
        cashPointsHistory.setUsername(username);
        cashPointsHistory.setPointsChange(-pointsUsed); // 사용한 포인트를 음수로 저장
        cashPointsHistory.setRemainingPoint(remainingPoints);
        cashPointsHistory.setActivityType(activityType);
        cashPointsHistory.setContent(itemName);
        cashPointsHistory.setCreatedAt(LocalDateTime.now());

        save(cashPointsHistory); // 이력 저장
    }

    public Page<CashPointsHistory> getCashPointsHistoryByUsername(String username, int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        if (search != null && !search.isEmpty()) {
            return repository.findByUsernameAndContentContainingOrderByCreatedAtDesc(username, search, pageable);
        } else {
            return repository.findByUsernameOrderByCreatedAtDesc(username, pageable);
        }
    }

    // 추가된 메소드
    public Page<CashPointsHistory> getCashPointsHistoryBySearch(String username, String search, Pageable pageable) {
        return repository.findByUsernameAndContentContainingOrderByCreatedAtDesc(username, search, pageable);
    }

    public Page<CashPointsHistory> getCashPointsHistory(String username, Pageable pageable) {
        return repository.findByUsernameOrderByCreatedAtDesc(username, pageable);
    }
}
