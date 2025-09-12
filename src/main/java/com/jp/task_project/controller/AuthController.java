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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestCreateDTO user_dto){
        UserResponseDTO userResponseDTO = userService.registerUserInBD(user_dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard")
    public ResponseEntity<String> adminDashboard() {
        return ResponseEntity.ok("Dashboard do ADMIN");
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseLoginDTO> doLogin(@RequestBody UserRequestLoginDTO userLogin) {
        try {
            // Autentica o usuário via Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLogin.email(), userLogin.pass())
            );

            // Se chegou aqui, autenticação OK
            // Pega o UserDetails (email, roles, etc)
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Gera o token JWT com base no email (username)
            String token = jwtUtil.generateToken(userDetails.getUsername());

            // Busca os dados do usuário para enviar no response (se quiser)
            UserResponseDTO userResponseDTO = userService.doLogin(userLogin);

            UserResponseLoginDTO responseUser = new UserResponseLoginDTO(userResponseDTO, token);

            return ResponseEntity.ok(responseUser);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }}

