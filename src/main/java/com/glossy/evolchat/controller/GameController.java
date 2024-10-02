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
import java.util.Map;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameCardInfoService gameCardInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private GameCardResultService gameCardResultService;

    private final SimpMessagingTemplate messagingTemplate;

    public GameController(GameCardResultService gameCardResultService, SimpMessagingTemplate messagingTemplate) {
        this.gameCardResultService = gameCardResultService;
        this.messagingTemplate = messagingTemplate;
    }

    // 메시지 전송 요청
//    @PostMapping("/send-message")
//    public ResponseEntity<Void> sendMessage(@RequestBody GameCardInfoRequest cardInfoRequest) {
//        GameCardInfo gameCardInfo = new GameCardInfo();
//
//        gameCardInfo.setId(cardInfoRequest.getId());
//        gameCardInfo.setGameId(cardInfoRequest.getGameId());
//        gameCardInfo.setCardPlace(cardInfoRequest.getCardPlace());
//        gameCardInfo.setCardNum(cardInfoRequest.getCardNum());
//        gameCardInfo.setCardCount(cardInfoRequest.getCardCount());
//
//        try {
//            gameCardInfoService.saveGameHistory(gameCardInfo);
//            messagingTemplate.convertAndSend("/topic/game/441", gameCardInfo); // 웹소켓 메시지 전송
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    // 웹소켓을 통한 메시지 전송
    @MessageMapping("/game-send/{gameRoomId}")
    @SendTo("/topic/game/{gameRoomId}")
    public GameCardInfo sendWebSocketMessage(
            @DestinationVariable("gameRoomId") String gameRoomId,
            Principal principal,
            @Payload Map<String, Object> payload
    ) {
        System.out.print("-------------------------");
        String username = principal.getName();
        User user = userService.findByUsername(username);

        // 카드 정보 추출
        String cardPlace = (String) payload.get("cardPlace");
        String cardNum = (String) payload.get("cardNum");
        String cardCount = (String) payload.get("cardCount");

        GameCardInfo gameCardInfo = new GameCardInfo();
        gameCardInfo.setGameId(gameRoomId);
        gameCardInfo.setCardPlace(cardPlace);
        gameCardInfo.setCardNum(cardNum);
        gameCardInfo.setCardCount(cardCount);

        // 메시지 저장 서비스 호출
        gameCardInfoService.saveGameHistory(gameCardInfo);

        // 게임 결과를 저장하는 로직 추가 (가정: payload에서 게임 결과 정보도 포함됨)
        if (payload.containsKey("gameResult")) {
            Map<String, Object> gameResult = (Map<String, Object>) payload.get("gameResult");

            GameCardResult gameCardResult = new GameCardResult();
            gameCardResult.setGameId(gameRoomId);
            gameCardResult.setPlayerScore((String) gameResult.get("playerScore"));
            gameCardResult.setBankerScore((String) gameResult.get("bankerScore"));
            gameCardResult.setWinner((String) gameResult.get("winner"));
            gameCardResult.setTimestamp(LocalDateTime.now());

            gameCardResultService.save(gameCardResult);
        }

        // 메시지를 클라이언트로 전송
        return gameCardInfo;
    }
}
