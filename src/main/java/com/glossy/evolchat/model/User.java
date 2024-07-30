package com.glossy.evolchat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String password;

    @Transient
    private String confirmPassword; // 비밀번호 확인을 위한 임시 필드

    @Column
    private String email;

    @Column(nullable = false)
    private int roleId;

    @Column(nullable = false)
    private String phone;  // 전화번호 필드 추가
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean isDeleted = false;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    private String nickname;
    private String profilePicture;
    private String homeBackgroundPicture;
    private String myHomeUrl;
    private String todaysMessage;
    private String country;
    private String region;
    private String gender;
    private boolean locationPublic;
    private String bankName;
    private String accountNumber;
    private String idCardPicture;
    private String interests;

    private int bettingProfit;
    private int bettingWins;
    private int bettingLosses;
    private int maxWinningStreak;

    @Transient
    private String winRate; // 승률을 위한 임시 필드

    public void setId(Long id) {
        this.id = id;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
