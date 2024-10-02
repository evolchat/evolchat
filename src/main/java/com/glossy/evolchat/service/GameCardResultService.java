package com.glossy.evolchat.service;

import com.glossy.evolchat.model.GameCardResult;
import com.glossy.evolchat.repository.GameCardResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameCardResultService {

    @Autowired
    private GameCardResultRepository gameCardResultRepository;

    // 게임 결과 저장 메서드
    public void save(GameCardResult gameCardResult) {
        gameCardResultRepository.save(gameCardResult);
    }
}
