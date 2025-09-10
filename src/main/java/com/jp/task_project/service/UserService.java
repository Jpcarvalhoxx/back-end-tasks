package com.jp.task_project.service;

import com.jp.task_project.dto.Task.TaskRequestCreateDTO;
import com.jp.task_project.dto.Users.UserRequestCreateDTO;
import com.jp.task_project.dto.Users.UserRequestLoginDTO;
import com.jp.task_project.dto.Users.UserRequestUpdateDTO;
import com.jp.task_project.dto.Users.UserResponseDTO;
import com.jp.task_project.entity.User.User;
import com.jp.task_project.exeception.EmailAlreadyExistsException;
import com.jp.task_project.exeception.UserNotFoundException;
import com.jp.task_project.exeception.UserNotLoginException;
import com.jp.task_project.mapper.UserMapper;
import com.jp.task_project.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO registerUserInBD(UserRequestCreateDTO user_dto) {
        String encryptedPassword = passwordEncoder.encode(user_dto.pass());

        User user = userMapper.toUserCreate(user_dto);
        user.setPassword(encryptedPassword);

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Email exists");
        }

        user = userRepository.save(user);
        return userMapper.toDto(user);

    }


    @Transactional
    public UserResponseDTO updateUserPutInBD(Long id, UserRequestUpdateDTO userRequestUpdateDTO) {


        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com id: " + id));

           user.setPassword(passwordEncoder.encode(userRequestUpdateDTO.pass()));
           userMapper.toUserUpdate(userRequestUpdateDTO,user);


        userRepository.save(user);

        return userMapper.toDto(user);
    }



    @Transactional
    public UserResponseDTO updatePatchUserInBD(Long id, UserRequestUpdateDTO userRequestUpdateDTO) {


        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com id: " + id));

        userMapper.partialUpdate(userRequestUpdateDTO,user);

        userRepository.save(user);

        return userMapper.toDto(user);
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário not found with: " + id));
        return userMapper.toDto(user);
    }

    public void deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário not found with: " + id));

        userRepository.deleteById(id);

    }

    public UserResponseDTO doLogin(UserRequestLoginDTO u) {
        User user = userRepository.findByEmailUser(u.email()).orElseThrow(() -> new UserNotFoundException("Credenciais de login inválidas"));

        if (passwordEncoder.matches(u.pass(), user.getPassword())) {
            // Se as senhas correspondem, retorna o DTO de sucesso
            return userMapper.toDto(user);
        } else {
            // Se as senhas não correspondem, lança uma exceção
            throw new UserNotLoginException("Credenciais de login inválidas");
        }

    }


}



