package com.glossy.evolchat.dto;

import java.time.LocalDateTime;

public class CashPointsHistoryDto {
    private Long id;
    private String content;
    private Long amount;
    private Long remainingBalance;
    private LocalDateTime createdAt;

    public CashPointsHistoryDto(Long id, String content, Long amount, Long remainingBalance, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.amount = amount;
        this.remainingBalance = remainingBalance;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(Long remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
