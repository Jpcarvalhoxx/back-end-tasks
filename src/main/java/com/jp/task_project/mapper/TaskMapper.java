package com.jp.task_project.mapper;

import com.jp.task_project.dto.Task.TaskRequestCreateDTO;
import com.jp.task_project.dto.Task.TaskRequestUpdateDTO;
import com.jp.task_project.dto.Task.TaskResponseDTO;
import com.jp.task_project.entity.Task.Task;
import com.jp.task_project.entity.User.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
    @Mapping(source = "id", target = "taskId")
    @Mapping(source = "user.id", target = "userId") // Mapeando o campo "user.id" da Task para "userId" no DTO
    TaskResponseDTO toDto(Task task);

    @Mapping(target = "user", ignore = true)
    Task toCreateTask(TaskRequestCreateDTO dto);

    List<TaskResponseDTO> toDtoList(List<Task> tasks);


    // Mapeia TaskRequestUpdateDTO para Task (atualiza o objeto existente)
    @Mapping(target = "user", ignore = true)  // Ignora o mapeamento do campo user
    void toUpdateTask(TaskRequestUpdateDTO dto, @MappingTarget Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", ignore = true)  // Ignora o campo user no mapeamento
    Task partialUpdate(TaskRequestUpdateDTO taskRequestUpdateDTO, @MappingTarget Task task);
}