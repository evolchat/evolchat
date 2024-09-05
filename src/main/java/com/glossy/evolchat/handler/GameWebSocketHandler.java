package com.glossy.evolchat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.core.type.TypeReference;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class GameWebSocketHandler extends TextWebSocketHandler {

    private final Consumer<Map<String, Object>> messageProcessor;

    public GameWebSocketHandler(Consumer<Map<String, Object>> messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

//    @Override
//    protected void handleTextMessage(WebSocketSession session, org.springframework.web.socket.TextMessage message) throws IOException {
//        System.out.println("handleTextMessage 수신 시작");
//        // Log the received message
//        System.out.println("Received WebSocket message: " + message.getPayload());
//
//        // Assuming the message payload might be in JSON or XML format
//        String payload = message.getPayload();
//        Map<String, Object> data = null;
//
//        if (payload.trim().startsWith("{") || payload.trim().startsWith("[")) {
//            // Parse JSON payload
//            data = parseJsonPayload(payload);
//        } else if (payload.trim().startsWith("<")) {
//            // Parse XML payload
//            data = parseXmlPayload(payload);
//        }
//
//        if (data != null) {
//            messageProcessor.accept(data);
//        } else {
//            System.out.println("Unsupported message format.");
//        }
//    }

    private Map<String, Object> parseJsonPayload(String payload) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    private Map<String, Object> parseXmlPayload(String payload) {
        Map<String, Object> data = new HashMap<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(payload.getBytes()));

            // Assuming a simple XML structure for demonstration
            NodeList cardList = doc.getElementsByTagName("card");
            for (int i = 0; i < cardList.getLength(); i++) {
                String sc = cardList.item(i).getAttributes().getNamedItem("sc").getTextContent();
                data.put("sc", sc);
                // Add more data extraction as needed
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
