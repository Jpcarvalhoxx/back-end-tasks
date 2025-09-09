package com.jp.task_project.controller;


import com.jp.task_project.dto.Users.UserRequestCreateDTO;
import com.jp.task_project.dto.Users.UserRequestLoginDTO;
import com.jp.task_project.dto.Users.UserRequestUpdateDTO;
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


    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO userResponseDTO = userService.getUserById(id);
        // Retorna 200 OK e o corpo da resposta
        return ResponseEntity.ok(userResponseDTO);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") Long id){

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}/update")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable("id") Long id,
            @RequestBody UserRequestUpdateDTO taskRequest) {
        System.out.println("kdqoqwdkpqwdkp");
       UserResponseDTO userResponseDTO = userService.updateUserInBD(id,taskRequest);
       return ResponseEntity.ok(userResponseDTO);
    }



}
