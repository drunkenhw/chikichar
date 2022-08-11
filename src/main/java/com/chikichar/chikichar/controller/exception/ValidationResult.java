package com.chikichar.chikichar.controller.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationResult {
    private List<FieldErrorDetail> errors;

    public static ValidationResult create(Errors errors){
        List<FieldErrorDetail> details = errors.getFieldErrors()
                .stream()
                .map(error -> FieldErrorDetail.create(error))
                .collect(Collectors.toList());
        return new ValidationResult(details);
    }
}
