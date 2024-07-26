package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
    User findByEmail(String email);

    Page<User> findAllByOrderByBettingProfitDesc(Pageable pageable);
}
