package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.UserPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPointsRepository extends JpaRepository<UserPoints, Integer> {
    Optional<UserPoints> findByUsername(String username);
}
