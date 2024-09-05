package com.glossy.evolchat.controller;

import com.glossy.evolchat.dto.PurchaseRequest;
import com.glossy.evolchat.model.Items;
import com.glossy.evolchat.model.Purchase;
import com.glossy.evolchat.model.User;
import com.glossy.evolchat.model.UserPoints;
import com.glossy.evolchat.service.*;
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

    @Autowired
    private CashPointsHistoryService cashPointsHistoryService;

    @Autowired
    private GoldChipPointsHistoryService goldChipPointsHistoryService;

    @Autowired
    private BettingPointsHistoryService bettingPointsHistoryService;

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

        int totalCost = item.getCcash() * quantity;
        int totalGoldChip = item.getGgoldChip() * quantity;
        int totalBettingPoints = item.getBbettingPoints() * quantity;

        boolean isSuccess = false;
        String itemName = item.getName() + " " + quantity + "개 <span>구매</span>";

        switch (paymentMethod.toLowerCase()) {
            case "ccash":
                if (userPoints.getCCash() >= totalCost) {
                    userPoints.setCCash(userPoints.getCCash() - totalCost);
                    userPointsService.save(userPoints);
                    isSuccess = true;

                    // 캐시 이력 테이블에 기록 (닉네임 변경권 2개 <span>구매</span> 형식으로)
                    cashPointsHistoryService.saveCashPointsHistory(username, totalCost, userPoints.getCCash(), "구매", itemName, quantity);
                } else {
                    return false; // 자금 부족
                }
                break;

            case "gold chip":
                if (userPoints.getGGoldChip() >= totalGoldChip) {
                    userPoints.setGGoldChip(userPoints.getGGoldChip() - totalGoldChip);
                    userPointsService.save(userPoints);
                    isSuccess = true;

                    // 골든칩 이력 테이블에 기록
                    goldChipPointsHistoryService.saveGoldChipPointsHistory(username, totalGoldChip, userPoints.getGGoldChip(), "구매", item.getName(), null, quantity, totalGoldChip, null);
                } else {
                    return false; // 자금 부족
                }
                break;

            case "betting points":
                if (userPoints.getBBettingPoints() >= totalBettingPoints) {
                    userPoints.setBBettingPoints(userPoints.getBBettingPoints() - totalBettingPoints);
                    userPointsService.save(userPoints);
                    isSuccess = true;

                    // 베팅 포인트 이력 테이블에 기록
                    bettingPointsHistoryService.saveBettingPointsHistory(username, totalBettingPoints, userPoints.getBBettingPoints(), "구매", item.getName(), quantity);
                } else {
                    return false; // 자금 부족
                }
                break;

            default:
                return false; // 유효하지 않은 결제 방법
        }

        if (isSuccess) {
            User user = userService.findByUsername(username);
            Purchase purchase = new Purchase(user, item, quantity, LocalDateTime.now());
            purchaseService.savePurchase(purchase);
        }

        return isSuccess;
    }
}