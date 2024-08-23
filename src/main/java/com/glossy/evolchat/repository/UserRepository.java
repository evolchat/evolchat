package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Username을 기준으로 사용자 검색
    Optional<User> findByUsername(String username);

    // 이메일을 기준으로 사용자 검색
    User findByEmail(String email);

    // BettingProfit 기준으로 사용자 목록을 내림차순으로 페이징 조회
    Page<User> findAllByOrderByBettingProfitDesc(Pageable pageable);

    Optional<User> findByNickname(String nickname);
}
