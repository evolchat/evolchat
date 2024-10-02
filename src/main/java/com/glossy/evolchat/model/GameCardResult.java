package com.glossy.evolchat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class GameCardResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Long 대신 int 사용

    @Column(nullable = false)
    private String gameId;

    @Column(nullable = false)
    private String playerScore;

    @Column(nullable = false)
    private String bankerScore;

    @Column(nullable = false)
    private String winner;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

}
