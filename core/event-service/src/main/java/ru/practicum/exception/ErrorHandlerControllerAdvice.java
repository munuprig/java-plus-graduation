package ru.practicum.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ErrorHandlerControllerAdvice extends BaseErrorHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {

        List<ValidationViolation> violations = e.getConstraintViolations()
                .stream()
                .map(v -> {
                    log.error("Constraint violation: {} - {}", v.getPropertyPath(), v.getMessage());
                    return new ValidationViolation(v.getPropertyPath().toString(), v.getMessage());
                })
                .collect(Collectors.toList());

        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError onDataIntegrity(org.springframework.dao.DataIntegrityViolationException e) {
        log.error("409 DataIntegrityViolation: {}", e.getMessage(), e);
        return buildError(HttpStatus.CONFLICT, "CONFLICT", e);
    }
}