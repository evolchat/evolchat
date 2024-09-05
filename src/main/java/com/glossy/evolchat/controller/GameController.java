//package com.glossy.evolchat.controller;
//
//import com.glossy.evolchat.model.GameResult;
//import com.glossy.evolchat.handler.GameWebSocketHandler;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONObject;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.socket.client.WebSocketClient;
//import org.springframework.web.socket.client.standard.StandardWebSocketClient;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/game")
//public class GameController {
//
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//
//    private final WebSocketClient client = new StandardWebSocketClient();
//    private String websocketUrl;
//    private String currentGame;
//    private final List<Map<String, Object>> gameData = new ArrayList<>();
//
//    @PostConstruct
//    public void initialize() {
//        try {
//            String gameUrl = fetchGameUrl();
//            String jsession = fetchJsessionFromGameUrl(gameUrl);
//            System.out.println("================================================================================");
//            System.out.println("jsession : " + jsession);
//            websocketUrl = "wss://games.pragmaticplaylives.net/game?JSESSIONID=" + jsession + "&tableId=bc281koreanch281&reconnect=true";
//            System.out.println("websocketUrl : " + websocketUrl);
//            startGameWebSocket();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void startGameWebSocket() {
//        client.doHandshake(new GameWebSocketHandler(this::processGameData), websocketUrl);
//    }
//
//    private void processGameData(Map<String, Object> data) {
//        // 메시지 원문 가져오기
//        String message = (String) data.get("sc");
//
//        // 메시지 필터링
//        if (message != null && message.trim().startsWith("<card sc=")) {
//            // 메시지 로그 출력
//            System.out.println("Received WebSocket message: " + message);
//            // 메시지에서 필요한 데이터 추출
//            String sc = (String) data.get("sc");
//            String game = (String) data.get("game");
//            String cardCount = (String) data.get("cardCount");
//            String place = (String) data.get("place");
//
//            // 데이터 출력
//            System.out.println("sc : " + sc);
//            System.out.println("game : " + game);
//            System.out.println("cardCount : " + cardCount);
//            System.out.println("place : " + place);
//
//            // 클라이언트에 카드 정보를 전송
//            messagingTemplate.convertAndSend("/topic/cards", data);
//
//            if (game == null) {
//                System.out.println("Received game data with null game value.");
//                return;
//            }
//
//            if (currentGame == null) {
//                currentGame = game;
//            }
//
//            if (game.equals(currentGame)) {
//                gameData.add(data);
//            } else {
//                // 이전 게임 결과를 도출하고 출력
//                GameResult result = determineWinner(gameData);
//                messagingTemplate.convertAndSend("/topic/gameResult", result);
//
//                // 새로운 게임으로 전환
//                currentGame = game;
//                gameData.clear();
//                gameData.add(data);
//            }
//        }
//    }
//
//    private GameResult determineWinner(List<Map<String, Object>> gameData) {
//        // 게임 데이터를 바탕으로 점수 계산
//        int playerScore = 0;
//        int dealerScore = 0;
//        boolean playerHasBlackjack = false;
//        boolean dealerHasBlackjack = false;
//
//        for (Map<String, Object> data : gameData) {
//            String place = (String) data.get("place");
//            String cardValue = (String) data.get("sc"); // 카드 값 추출
//            int cardScore = getCardScore(cardValue);
//
//            if ("player".equals(place)) {
//                playerScore += cardScore;
//                if (cardValue.equals("Blackjack")) {
//                    playerHasBlackjack = true;
//                }
//            } else if ("dealer".equals(place)) {
//                dealerScore += cardScore;
//                if (cardValue.equals("Blackjack")) {
//                    dealerHasBlackjack = true;
//                }
//            }
//        }
//
//        // 승자 결정
//        String winner = "dealer";
//        if (playerHasBlackjack && !dealerHasBlackjack) {
//            winner = "player";
//        } else if (playerScore > dealerScore) {
//            winner = "player";
//        } else if (dealerScore > playerScore) {
//            winner = "dealer";
//        } else {
//            winner = "draw";
//        }
//
//        return new GameResult(winner, playerScore, dealerScore, playerHasBlackjack, dealerHasBlackjack);
//    }
//
//    // 카드 값에 따른 점수 계산
//    private int getCardScore(String cardValue) {
//        switch (cardValue) {
//            case "Ace":
//                return 11;
//            case "King":
//            case "Queen":
//            case "Jack":
//                return 10;
//            default:
//                try {
//                    return Integer.parseInt(cardValue);
//                } catch (NumberFormatException e) {
//                    return 0;
//                }
//        }
//    }
//
//    private String fetchGameUrl() throws IOException {
//        // Create an HTTP client and prepare the POST request
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            HttpPost httpPost = new HttpPost("https://sc4-api-prd.dreamgates.net/v4/game/game-url");
//
//
//            // Set request headers
//            httpPost.setHeader("Accept", "application/json");
//            httpPost.setHeader("Authorization", "Bearer 13f9e94f-0160-42ff-961b-f74c6defab4c");
//            httpPost.setHeader("Content-Type", "application/json");
//
//            // Set request body
//            String jsonBody = new JSONObject()
//                    .put("user_code", 400239376)
//                    .put("provider_id", 1)
//                    .put("game_symbol", "441")
//                    .put("lang", 1)
//                    .put("return_url", "https://lobby.slotcity.com")
//                    .put("win_ratio", 0)
//                    .toString();
//
//            httpPost.setEntity(new org.apache.http.entity.StringEntity(jsonBody));
//
//            // Execute the request
//            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
//                if (response.getStatusLine().getStatusCode() == 200) {
//                    // Parse the response to get the game URL
//                    String responseBody = EntityUtils.toString(response.getEntity());
//                    JSONObject jsonResponse = new JSONObject(responseBody);
//                    return jsonResponse.getJSONObject("data").getString("game_url");
//                } else {
//                    throw new IOException("Failed to get game URL: " + response.getStatusLine());
//                }
//            }
//        }
//    }
//
//    private String fetchJsessionFromGameUrl(String gameUrl) {
//        // Set up ChromeDriver options
//        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver.exe");
//        ChromeOptions options = new ChromeOptions();
////        options.addArguments("--headless"); // Run in headless mode if needed
//
//        WebDriver driver = null;
//        try {
//            // Initialize the ChromeDriver
//            driver = new ChromeDriver(options);
//            driver.get(gameUrl);
//
//            // Wait for 30 seconds (30,000 milliseconds)
//            Thread.sleep(1800000);
//
//            // Locate the element by CSS Selector and fetch the 'jsession' attribute
//            WebElement element = driver.findElement(By.cssSelector("wc-game-promotions"));
//            String jsession = element.getAttribute("jsession");
//
//            return jsession;
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            return null;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            if (driver != null) {
//                driver.quit(); // Ensure the WebDriver is properly closed
//            }
//        }
//    }
//}
