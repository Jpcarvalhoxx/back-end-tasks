package com.jp.task_project.dto.Users;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserResponseDTO {
    private Long id_user;
    private String name;
    private String email;
    private String pass;

}
