package com.jp.task_project.dto.Task;

import com.jp.task_project.utils.enums.TaskStatus;
import com.jp.task_project.utils.enums.TaskType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TaskRequestCreateDTO {

    private Long userId;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskType type;
    private LocalDateTime createdAt;
    private LocalDateTime endAt;
}
