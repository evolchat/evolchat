package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.GameCardResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameCardResultRepository extends JpaRepository<GameCardResult, Long> {
    // 특정 gameTypeNum에 따른 최신 게임 결과 가져오기
    List<GameCardResult> findByGameTypeNum(String gameTypeNum);
}