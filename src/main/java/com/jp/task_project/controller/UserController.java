package com.jp.task_project.controller;


import com.jp.task_project.dto.Users.UserRequestCreateDTO;
import com.jp.task_project.dto.Users.UserResponseDTO;
import com.jp.task_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/users")

public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody UserRequestCreateDTO user_dto){
        UserResponseDTO userResponseDTO = userService.registerUserInBD(user_dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO userResponseDTO = userService.getUserById(id);

        if (userResponseDTO == null) {
            // Retorna 404 Not Found se o usuário não for encontrado
            return ResponseEntity.notFound().build();
        }

        // Retorna 200 OK e o corpo da resposta
        return ResponseEntity.ok(userResponseDTO);
    }



}
