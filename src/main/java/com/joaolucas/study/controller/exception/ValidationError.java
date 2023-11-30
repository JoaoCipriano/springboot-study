package com.joaolucas.study.controller.exception;

import lombok.Getter;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError extends StandardError {

    @Serial
    private static final long serialVersionUID = -3414487349737220956L;

    private final List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    public void addError(String fieldName, String message) {
        errors.add(new FieldMessage(fieldName, message));
    }
}
