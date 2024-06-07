package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.UserPoints;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPointsRepository extends JpaRepository<UserPoints, Integer> {
}
