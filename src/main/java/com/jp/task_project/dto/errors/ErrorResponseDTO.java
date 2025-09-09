package com.jp.task_project.dto.errors;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseDTO(String message, int status, LocalDateTime times_tamp, List<FieldError> errors) {

    public record FieldError(String field, String errorMessage) {
    }
}
