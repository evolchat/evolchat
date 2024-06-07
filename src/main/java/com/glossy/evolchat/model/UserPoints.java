package com.glossy.evolchat.model;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class UserPoints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(nullable = false)
    private int userId;

    @Column(nullable = false)
    private int cCash;

    @Column(nullable = false)
    private int bBettingPoints;

    @Column(nullable = false)
    private int aActivityPoints;

    @Column(nullable = false)
    private int gGoldChip;
}
