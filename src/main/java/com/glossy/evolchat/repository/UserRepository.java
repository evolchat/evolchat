package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
