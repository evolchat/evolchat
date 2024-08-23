package com.glossy.evolchat.service;

import com.glossy.evolchat.model.Items;
import com.glossy.evolchat.model.Purchase;
import com.glossy.evolchat.model.UserPoints;
import com.glossy.evolchat.repository.PurchaseRepository;
import com.glossy.evolchat.repository.UserPointsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Transactional
    public void savePurchase(Purchase purchase) {
        purchaseRepository.save(purchase);
    }
}
