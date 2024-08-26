package com.glossy.evolchat.repository;

import com.glossy.evolchat.dto.ItemPurchaseSummary;
import com.glossy.evolchat.model.Purchase;
import com.glossy.evolchat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    @Query("SELECT p.item.id, p.item.type, p.item.name, p.item.description, SUM(p.quantity) " +
            "FROM Purchase p " +
            "WHERE p.user = :user " +
            "GROUP BY p.item.id, p.item.type, p.item.name, p.item.description")
    List<Object[]> findItemPurchaseSummariesByUser(User user);
}