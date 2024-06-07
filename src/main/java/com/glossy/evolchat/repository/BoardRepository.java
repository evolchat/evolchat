package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
}
