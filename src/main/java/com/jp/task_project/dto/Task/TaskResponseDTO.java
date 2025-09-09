package com.jp.task_project.dto.Task;

import com.jp.task_project.entity.Task.Task;
import com.jp.task_project.utils.enums.TaskStatus;
import com.jp.task_project.utils.enums.TaskType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


public record TaskResponseDTO (
     Long taskId,
     Long userId,
     String title,
     String description,
     TaskStatus status,
     TaskType type,
     LocalDateTime createdAt,
     LocalDateTime endAt
){


    public static TaskResponseDTO from(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getUser().getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getType(),
                task.getCreatedAt(),
                task.getEndAt()
        );
    }


}
