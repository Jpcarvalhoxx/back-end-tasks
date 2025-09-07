package com.jp.task_project.dto.Users.errors;

import java.util.List;

public record ErrorResponseDTO (String message, int status, List<FieldError> errors){

    public record FieldError(String field, String errorMessage) {}
}
