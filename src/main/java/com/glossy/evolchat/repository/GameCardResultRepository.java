package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.GameCardResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameCardResultRepository extends JpaRepository<GameCardResult, Integer> {
}
