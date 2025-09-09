package com.jp.task_project.dto.Users;

import com.jp.task_project.entity.User.User;


public record UserRequestUpdateDTO(String name, String email, String pass) {
    public static User convertToUser(UserRequestUpdateDTO u, String encryptedPassword) {
        return new User(u.name, u.email, encryptedPassword);
    }

}