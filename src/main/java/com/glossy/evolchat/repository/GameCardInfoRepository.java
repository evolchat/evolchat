package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.FriendChatMessage;
import com.glossy.evolchat.model.GameCardInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameCardInfoRepository extends JpaRepository<GameCardInfo, Integer> {
}