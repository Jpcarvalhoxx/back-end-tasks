package com.jp.task_project.dto.Task;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record RegisterImageTaskDTO( List<MultipartFile>imgs) {

}
