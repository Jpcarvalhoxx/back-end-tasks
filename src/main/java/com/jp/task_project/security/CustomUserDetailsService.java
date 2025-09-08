package com.jp.task_project.security;


import com.jp.task_project.entity.User.User;
import com.jp.task_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.repository.findByEmailUser(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.
                User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}