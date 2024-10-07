package com.glossy.evolchat.controller;

import com.glossy.evolchat.dto.*;
import com.glossy.evolchat.model.*;
import com.glossy.evolchat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameCardInfoService gameCardInfoService;

    @Autowired
    private GameCardResultService gameCardResultService;

    // 특정 gameTypeNum에 해당하는 최신 game_id의 카드 정보 가져오기
    @GetMapping("/latest-card-info/{gameTypeNum}")
    public ResponseEntity<List<GameCardInfo>> getLatestCardInfo(@PathVariable String gameTypeNum) {
        try {
            List<GameCardInfo> cardInfoList = gameCardInfoService.findLatestCardsByGameTypeNum(gameTypeNum);

            return !cardInfoList.isEmpty()
                    ? ResponseEntity.ok(cardInfoList)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 특정 gameTypeNum에 따른 최신 게임 결과 가져오기
    @GetMapping("/game-result/{gameTypeNum}")
    public ResponseEntity<List<GameCardResult>> getGameCardResult(@PathVariable String gameTypeNum) {
        try {
            List<GameCardResult> gameCardResultList = gameCardResultService.findByGameTypeNum(gameTypeNum);
            return !gameCardResultList.isEmpty()
                    ? ResponseEntity.ok(gameCardResultList)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}