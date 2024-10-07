package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.GameCardInfo;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface GameCardInfoRepository extends JpaRepository<GameCardInfo, Long> {

    // 특정 gameTypeNum에 해당하는 마지막 game_id 조회
    @Query("SELECT g.gameId FROM GameCardInfo g WHERE g.gameTypeNum = :gameTypeNum ORDER BY g.timestamp DESC LIMIT 1")
    String findLatestGameIdByGameTypeNum(@Param("gameTypeNum") String gameTypeNum);

    // 특정 game_id와 gameTypeNum에 해당하는 카드 정보 가져오기
    @Query("SELECT g FROM GameCardInfo g WHERE g.gameId = :gameId AND g.gameTypeNum = :gameTypeNum ORDER BY g.timestamp DESC")
    List<GameCardInfo> findCardsByGameIdAndGameTypeNum(@Param("gameId") String gameId, @Param("gameTypeNum") String gameTypeNum);
}