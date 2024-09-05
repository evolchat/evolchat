package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.CashPointsHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashPointsHistoryRepository extends JpaRepository<CashPointsHistory, Long> {
    Page<CashPointsHistory> findByUsernameOrderByCreatedAtDesc(String username, Pageable pageable);
    Page<CashPointsHistory> findByUsernameAndContentContainingOrderByCreatedAtDesc(String username, String content, Pageable pageable);
}
