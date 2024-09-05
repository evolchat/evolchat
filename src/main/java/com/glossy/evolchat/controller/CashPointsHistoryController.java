package com.glossy.evolchat.controller;

import com.glossy.evolchat.dto.CashPointsHistoryDto;
import com.glossy.evolchat.model.CashPointsHistory;
import com.glossy.evolchat.service.CashPointsHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CashPointsHistoryController {

    @Autowired
    private CashPointsHistoryService cashPointsHistoryService;

    @GetMapping("/cash_transactions")
    public ResponseEntity<Map<String, Object>> getCashTransactions(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "User not authenticated."));
        }

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("createdAt")));
        Page<CashPointsHistory> cashPointsPage;

        if (search != null && !search.isEmpty()) {
            cashPointsPage = cashPointsHistoryService.getCashPointsHistoryBySearch(username, search, pageable);
        } else {
            cashPointsPage = cashPointsHistoryService.getCashPointsHistory(username, pageable);
        }

        List<CashPointsHistoryDto> cashPointsHistories = cashPointsPage.getContent().stream()
                .map(history -> new CashPointsHistoryDto(
                        (long) history.getSeq(),  // ID는 Long으로 변환
                        history.getContent(),
                        (long) history.getPointsChange(),  // Amount는 Long으로 변환
                        (long) history.getRemainingPoint(),  // Remaining balance는 Long으로 변환
                        history.getCreatedAt()))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("cashPointsHistories", cashPointsHistories);
        response.put("totalPages", cashPointsPage.getTotalPages());
        response.put("currentPage", cashPointsPage.getNumber() + 1);
        response.put("search", search);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Pages", String.valueOf(cashPointsPage.getTotalPages()));

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}
