package com.springBoot.blogApplication.repository;

import com.springBoot.blogApplication.entity.User;
import com.springBoot.blogApplication.services.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
