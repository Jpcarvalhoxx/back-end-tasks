package com.jp.task_project.service;

import com.jp.task_project.dto.Users.UserRequestCreateDTO;
import com.jp.task_project.dto.Users.UserResponseDTO;
import com.jp.task_project.entity.User.User;
import com.jp.task_project.exeception.EmailAlreadyExistsException;
import com.jp.task_project.exeception.UserNotFoundException;
import com.jp.task_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com id: " + id));
        return UserResponseDTO.from(user);
    }
}



