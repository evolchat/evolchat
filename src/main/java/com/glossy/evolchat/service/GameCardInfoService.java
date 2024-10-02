package com.glossy.evolchat.service;

import com.glossy.evolchat.dto.FriendChatMessageDTO;
import com.glossy.evolchat.model.FriendChatMessage;
import com.glossy.evolchat.model.GameCardInfo;
import com.glossy.evolchat.repository.FriendChatMessageRepository;
import com.glossy.evolchat.repository.GameCardInfoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameCardInfoService {

    @Autowired
    private GameCardInfoRepository gameCardInfoRepository;

    // 새로운 메시지 저장하기
    public void saveGameHistory(GameCardInfo cardInfo) {
        // timestamp 필드 설정
        cardInfo.setTimestamp(LocalDateTime.now());
        gameCardInfoRepository.save(cardInfo);
    }

}
