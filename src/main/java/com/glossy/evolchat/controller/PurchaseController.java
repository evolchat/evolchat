package com.glossy.evolchat.controller;

import com.glossy.evolchat.dto.PurchaseRequest;
import com.glossy.evolchat.model.Items;
import com.glossy.evolchat.model.Purchase;
import com.glossy.evolchat.model.User;
import com.glossy.evolchat.model.UserPoints;
import com.glossy.evolchat.service.PurchaseService;
import com.glossy.evolchat.service.ItemService;
import com.glossy.evolchat.service.UserPointsService;
import com.glossy.evolchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;

@Controller
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserPointsService userPointsService;

    @Autowired
    private UserService userService;

    @PostMapping("/purchase")
    @ResponseBody
    public ResponseEntity<?> purchaseItem(
            @RequestBody PurchaseRequest requestData,
            Principal principal) {

        String username = principal.getName();
        Items item = itemService.findById(requestData.getItemId());

        if (item == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Item not found"));
        }

        boolean purchaseSuccess = processPurchase(username, item, requestData.getQuantity(), requestData.getPaymentMethod());

        if (!purchaseSuccess) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Purchase failed"));
        }

        return ResponseEntity.ok(Map.of("success", true, "message", "Purchase successful"));
    }

    private boolean processPurchase(String username, Items item, int quantity, String paymentMethod) {
        UserPoints userPoints = userPointsService.findByUsername(username);
        if (userPoints == null) {
            return false; // 사용자 포인트 정보가 없으면 실패
        }

        // 총 비용 계산
        int totalCost = item.getCcash() * quantity;
        int totalGoldChip = item.getGgoldChip() * quantity;
        int totalBettingPoints = item.getBbettingPoints() * quantity;

        switch (paymentMethod.toLowerCase()) {
            case "ccash":
                if (userPoints.getCCash() >= totalCost) {
                    userPoints.setCCash(userPoints.getCCash() - totalCost);
                } else {
                    return false; // 자금 부족
                }
                break;

            case "gold chip":
                if (userPoints.getGGoldChip() >= totalGoldChip) {
                    userPoints.setGGoldChip(userPoints.getGGoldChip() - totalGoldChip);
                } else {
                    return false; // 자금 부족
                }
                break;

            case "betting points":
                if (userPoints.getBBettingPoints() >= totalBettingPoints) {
                    userPoints.setBBettingPoints(userPoints.getBBettingPoints() - totalBettingPoints);
                } else {
                    return false; // 자금 부족
                }
                break;

            default:
                return false; // 유효하지 않은 결제 방법
        }

        userPointsService.save(userPoints);

        // 현재 사용자 정보 가져오기
        User user = userService.findByUsername(username);

        // Purchase 객체 생성 및 저장
        Purchase purchase = new Purchase(user, item, quantity, LocalDateTime.now());
        purchaseService.savePurchase(purchase);

        return true;
    }
}
