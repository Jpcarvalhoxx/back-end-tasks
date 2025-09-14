package com.jp.task_project.dto.Task;

import com.jp.task_project.entity.Image.Image;
import com.jp.task_project.utils.enums.TaskStatus;
import com.jp.task_project.utils.enums.TaskType;


import java.time.LocalDateTime;
import java.util.List;


public record TaskResponseDTO (
     Long taskId,
     Long userId,
     String title,
     String description,
     TaskStatus status,
     TaskType type,
     LocalDateTime createdAt,
     LocalDateTime endAt,
     List<ImageResponseDTO> imgs
){
    public record ImageResponseDTO(
            String imageUrl
    ) {}
}
