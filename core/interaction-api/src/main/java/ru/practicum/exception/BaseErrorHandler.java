package ru.practicum.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@Slf4j
public abstract class BaseErrorHandler {

    protected ApiError build(HttpStatus status, String reason, Throwable e) {
        return new ApiError(
                status.name(),
                reason,
                e.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(NotFoundException e) {
        log.error("404: {}", e.getMessage(), e);
        return build(HttpStatus.NOT_FOUND, "not found", e);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflict(ConflictException e) {
        log.error("409: {}", e.getMessage(), e);
        return build(HttpStatus.CONFLICT, "conflict", e);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequest(BadRequestException e) {
        log.error("400: {}", e.getMessage(), e);
        return build(HttpStatus.BAD_REQUEST, "bad request", e);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDataIntegrity(DataIntegrityViolationException e) {
        log.error("DB CONFLICT: {}", e.getMessage(), e);
        return build(HttpStatus.CONFLICT, "integrity violation", e);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleAny(Throwable e) {
        log.error("500: {}", e.getMessage(), e);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "internal error", e);
    }

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleConstraint(ConstraintViolationException e) {

        return new ValidationErrorResponse(
                e.getConstraintViolations().stream()
                        .map(v -> new ValidationViolation(
                                v.getPropertyPath().toString(),
                                v.getMessage()
                        ))
                        .toList()
        );
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleMethodArg(MethodArgumentNotValidException e) {

        return new ValidationErrorResponse(
                e.getBindingResult().getFieldErrors().stream()
                        .map(err -> new ValidationViolation(
                                err.getField(),
                                err.getDefaultMessage()
                        ))
                        .toList()
        );
    }
}