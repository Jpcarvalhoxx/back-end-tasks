package com.jp.task_project.service;

import com.jp.task_project.dto.Users.UserRequestCreateDTO;
import com.jp.task_project.dto.Users.UserResponseDTO;
import com.jp.task_project.entity.User.User;
import com.jp.task_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO registerUserInBD(UserRequestCreateDTO user_dto){
        String encryptedPassword = passwordEncoder.encode(user_dto.getPass());
        User u = convertUserDtoRequestToEntity(user_dto,encryptedPassword);
        u  = userRepository.save(u);
        return convertUserEntityInDtoResponse(u);

    }

    public UserResponseDTO getUserById(Long id){
        Optional<User> user = userRepository.findUserById(id);

        if (user.isPresent()){
            return  convertUserEntityInDtoResponse(user.get());
        }
        return null;
    }


    private User convertUserDtoRequestToEntity(UserRequestCreateDTO user_dto, String encryptedPassword){
        User u = new User();
        u.setName(user_dto.getName());
        u.setEmail(user_dto.getEmail());
        u.setPassword(encryptedPassword);

        return u;

    }

    private UserResponseDTO convertUserEntityInDtoResponse(User user){
        UserResponseDTO u = new UserResponseDTO();
        u.setId_user(user.getId());
        u.setName(user.getName());
        u.setEmail(user.getEmail());
        return u;

    }



}
