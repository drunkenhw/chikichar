package com.chikichar.chikichar.controller.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;

import java.util.Locale;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FieldErrorDetail {
    private String field;
    private String message;

    public static FieldErrorDetail create(FieldError error) {
        return new FieldErrorDetail(
                error.getField(),
                error.getDefaultMessage()
        );
    }
}
