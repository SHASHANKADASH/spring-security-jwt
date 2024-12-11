package com.shashanka.springsecuritywings1.bookmanagement.user.repo;

import com.shashanka.springsecuritywings1.bookmanagement.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
