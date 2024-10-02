package com.glossy.evolchat.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GameCardInfoRequest {
    private int id; // Long 대신 int 사용
    private String gameId;
    private String cardPlace;
    private String cardNum;
    private String cardCount;
}
