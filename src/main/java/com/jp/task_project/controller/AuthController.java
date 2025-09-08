package com.jp.task_project.controller;

import com.jp.task_project.dto.Users.UserRequestCreateDTO;
import com.jp.task_project.dto.Users.UserRequestLoginDTO;
import com.jp.task_project.dto.Users.UserResponseDTO;
import com.jp.task_project.dto.Users.UserResponseLoginDTO;
import com.jp.task_project.security.JwtUtil;
import com.jp.task_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestCreateDTO user_dto){
        UserResponseDTO userResponseDTO = userService.registerUserInBD(user_dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    @PostMapping ("/login")
    public ResponseEntity<UserResponseLoginDTO> dologin(@RequestBody UserRequestLoginDTO user_login){
        UserResponseDTO userResponseDTO = userService.doLogin(user_login);

        String token = jwtUtil.generateToken(userResponseDTO.email());
        UserResponseLoginDTO responseUser =  new UserResponseLoginDTO(userResponseDTO,token);
        return ResponseEntity.ok(responseUser);
    }


}
