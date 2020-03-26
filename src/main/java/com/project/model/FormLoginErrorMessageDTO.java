package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FormLoginErrorMessageDTO {
    private boolean hasError;
    private String message;
}
