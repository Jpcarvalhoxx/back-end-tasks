package com.jp.task_project.dto.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public record UserRequestLoginDTO(String email, String pass) {
}
