package com.jp.task_project.repository;

import com.jp.task_project.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    boolean existsByEmail(String email);
    Optional<User> findUserById(Long userId);

}