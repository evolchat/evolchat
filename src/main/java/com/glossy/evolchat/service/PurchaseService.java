package com.glossy.evolchat.service;

import com.glossy.evolchat.dto.ItemPurchaseSummary;
import com.glossy.evolchat.model.Purchase;
import com.glossy.evolchat.model.User;
import com.glossy.evolchat.repository.PurchaseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Transactional
    public void savePurchase(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    @Transactional
    public List<ItemPurchaseSummary> getItemPurchaseSummariesByUser(User user) {
        List<Object[]> results = purchaseRepository.findItemPurchaseSummariesByUser(user);
        List<ItemPurchaseSummary> summaries = new ArrayList<>();

        for (Object[] result : results) {
            try {
                int itemId = ((Number) result[0]).intValue(); // Use intValue() for ID
                String type = (String) result[1];
                String name = (String) result[2];
                String description = (String) result[3];
                long totalQuantity = ((Number) result[4]).longValue(); // Use longValue() to handle SUM

                ItemPurchaseSummary summary = new ItemPurchaseSummary(itemId, type, name, description, totalQuantity);
                summaries.add(summary);
            } catch (ClassCastException e) {
                e.printStackTrace();
                // Log or handle exception
            }
        }

        return summaries;
    }

    public int getMyHomeUploadTicketCount(User user) {
        Optional<Purchase> purchase = purchaseRepository.findByUserAndItemId(user, 3);
        return purchase.map(Purchase::getQuantity).orElse(0);
    }

    public int getProfileUploadTicketCount(User user) {
        Optional<Purchase> purchase = purchaseRepository.findByUserAndItemId(user, 2);
        return purchase.map(Purchase::getQuantity).orElse(0);
    }
}
