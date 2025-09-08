package com.jp.task_project.service;

import com.jp.task_project.dto.Users.UserRequestCreateDTO;
import com.jp.task_project.dto.Users.UserRequestLoginDTO;
import com.jp.task_project.dto.Users.UserResponseDTO;
import com.jp.task_project.entity.User.User;
import com.jp.task_project.exeception.EmailAlreadyExistsException;
import com.jp.task_project.exeception.UserNotFoundException;
import com.jp.task_project.exeception.UserNotLoginException;
import com.jp.task_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO registerUserInBD(UserRequestCreateDTO user_dto) {
        String encryptedPassword = passwordEncoder.encode(user_dto.pass());
        User u = UserRequestCreateDTO.convertToUser(user_dto, encryptedPassword);

        if (userRepository.existsByEmail(u.getEmail())) {
            throw new EmailAlreadyExistsException("Email já cadastrado");
        }

        u = userRepository.save(u);
        return UserResponseDTO.from(u);

    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com id: " + id));
        return UserResponseDTO.from(user);
    }

    public void deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com id: " + id));

        userRepository.deleteById(id);

    }

    public UserResponseDTO doLogin(UserRequestLoginDTO u) {
        User user = userRepository.findByEmailUser(u.email()).orElseThrow(() -> new UserNotFoundException("Credenciais de login inválidas"));

        if (passwordEncoder.matches(u.pass(), user.getPassword())) {
            // Se as senhas correspondem, retorna o DTO de sucesso
            return UserResponseDTO.from(user);
        } else {
            // Se as senhas não correspondem, lança uma exceção
            throw new UserNotLoginException("Credenciais de login inválidas");
        }

    }
}



