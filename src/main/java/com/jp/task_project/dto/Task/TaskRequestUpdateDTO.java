package com.jp.task_project.dto.Task;

import com.jp.task_project.entity.Task.Task;
import com.jp.task_project.utils.enums.TaskStatus;
import com.jp.task_project.utils.enums.TaskType;


public record TaskRequestUpdateDTO (
        Long userId,
        String title,
        String description,
        TaskStatus status,
        TaskType type

){

}