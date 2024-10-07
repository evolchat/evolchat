package com.glossy.evolchat.service;

import com.glossy.evolchat.model.GameCardResult;
import com.glossy.evolchat.repository.GameCardResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameCardResultService {

    @Autowired
    private GameCardResultRepository gameCardResultRepository;

    // 특정 gameTypeNum에 따른 모든 게임 결과 가져오기
    public List<GameCardResult> findByGameTypeNum(String gameTypeNum) {
        return gameCardResultRepository.findByGameTypeNum(gameTypeNum);
    }
}