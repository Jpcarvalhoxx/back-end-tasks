package com.jp.task_project.mapper;

import com.jp.task_project.dto.Users.UserRequestCreateDTO;
import com.jp.task_project.dto.Users.UserRequestUpdateDTO;
import com.jp.task_project.dto.Users.UserResponseDTO;
import com.jp.task_project.entity.User.User;
import org.mapstruct.*;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toEntity(UserResponseDTO userResponseDTO);
    @Mapping(source = "id", target = "id_user")
    UserResponseDTO toDto(User user);

    @Mapping(source = "pass", target = "password", ignore = true)
    User toUserCreate(UserRequestCreateDTO dto);
    @Mapping(source = "pass", target = "password")
    User toUserUpdate(UserRequestUpdateDTO dto,@MappingTarget User u);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", ignore = true)
    User partialUpdate(UserRequestUpdateDTO userRequestUpdateDTO, @MappingTarget User user);
}