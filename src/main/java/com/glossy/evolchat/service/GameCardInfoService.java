package com.glossy.evolchat.service;

import com.glossy.evolchat.model.GameCardInfo;
import com.glossy.evolchat.repository.GameCardInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class GameCardInfoService {

    @Autowired
    private GameCardInfoRepository gameCardInfoRepository;

    // 특정 gameTypeNum에 해당하는 최신 game_id의 카드 정보 가져오기
    public List<GameCardInfo> findLatestCardsByGameTypeNum(String gameTypeNum) {
        // 최신 game_id를 조회
        String latestGameId = gameCardInfoRepository.findLatestGameIdByGameTypeNum(gameTypeNum);
        if (latestGameId != null) {
            return gameCardInfoRepository.findCardsByGameIdAndGameTypeNum(latestGameId, gameTypeNum);
        }
        return List.of(); // 없으면 빈 리스트 반환
    }
}