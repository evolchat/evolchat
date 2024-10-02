package com.glossy.evolchat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class GameCardInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Long 대신 int 사용

    @Column(nullable = false)
    private String gameId;

    @Column(nullable = false)
    private String cardPlace;

    @Column(nullable = false)
    private String cardNum;

    @Column(nullable = false)
    private String cardCount;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

}
