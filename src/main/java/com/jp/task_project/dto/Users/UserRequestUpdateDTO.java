package com.jp.task_project.dto.Users;

import com.jp.task_project.entity.User.User;


public record UserRequestUpdateDTO(String name, String email, String pass) {

}