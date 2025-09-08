package com.jp.task_project.repository;

import com.jp.task_project.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    Optional<User> findUserById(Long userId);
    void deleteById(Long id);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmailUser(@Param("email") String email);

}