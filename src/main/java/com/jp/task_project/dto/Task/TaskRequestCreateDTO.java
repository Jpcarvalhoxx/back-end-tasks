package com.jp.task_project.dto.Task;

import com.jp.task_project.entity.Task.Task;
import com.jp.task_project.entity.User.User;
import com.jp.task_project.utils.enums.TaskStatus;
import com.jp.task_project.utils.enums.TaskType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public record TaskRequestCreateDTO (

        String title,
        String description,
        TaskStatus status,
        TaskType type,
        List<MultipartFile> imgs
){

}
