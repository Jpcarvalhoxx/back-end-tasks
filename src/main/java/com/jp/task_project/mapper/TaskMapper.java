package com.jp.task_project.mapper;

import com.jp.task_project.dto.Task.TaskRequestCreateDTO;
import com.jp.task_project.dto.Task.TaskRequestUpdateDTO;
import com.jp.task_project.dto.Task.TaskResponseDTO;
import com.jp.task_project.entity.Image.Image;
import com.jp.task_project.entity.Task.Task;

import org.mapstruct.*;

import java.util.List;
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {

    @Mapping(source = "id", target = "taskId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "imgs", target = "imgs")  // Mapeia lista de imagens
    TaskResponseDTO toDto(Task task);


    List<TaskResponseDTO.ImageResponseDTO> mapImagesToDTO(List<Image> imgs);

    // Mapeamento individual de uma imagem de Image para ImageResponseDTO
    TaskResponseDTO.ImageResponseDTO toImageDto(Image image);
    @Mapping(target = "user", ignore = true)
    Task toCreateTask(TaskRequestCreateDTO dto);

    List<TaskResponseDTO> toDtoList(List<Task> tasks);


    @Mapping(target = "user", ignore = true)
    void toUpdateTask(TaskRequestUpdateDTO dto, @MappingTarget Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", ignore = true)
    Task partialUpdate(TaskRequestUpdateDTO taskRequestUpdateDTO, @MappingTarget Task task);
}
