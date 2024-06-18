package com.glossy.evolchat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserPoints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private int cCash;

    @Column(nullable = false)
    private int bBettingPoints;

    @Column(nullable = false)
    private int aActivityPoints;

    @Column(nullable = false)
    private int gGoldChip;

    public UserPoints() {
    }

    public UserPoints(String username, int cCash, int bBettingPoints, int aActivityPoints, int gGoldChip) {
        this.username = username;
        this.cCash = cCash;
        this.bBettingPoints = bBettingPoints;
        this.aActivityPoints = aActivityPoints;
        this.gGoldChip = gGoldChip;
    }
}
