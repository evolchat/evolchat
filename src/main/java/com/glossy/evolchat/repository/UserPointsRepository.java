package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.UserPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPointsRepository extends JpaRepository<UserPoints, Integer> {

    UserPoints findByUsername(String username);
}
