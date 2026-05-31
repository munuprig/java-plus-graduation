package ru.practicum.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // =========================
    // 400 - Validation (Bean Validation)
    // =========================
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleConstraintViolation(ConstraintViolationException e) {

        List<ValidationViolation> violations = e.getConstraintViolations()
                .stream()
                .map(v -> new ValidationViolation(
                        v.getPropertyPath().toString(),
                        v.getMessage()
                ))
                .collect(Collectors.toList());

        log.error("ConstraintViolationException: {}", e.getMessage(), e);

        return new ValidationErrorResponse(violations);
    }

    // =========================
    // 400 - DTO validation / request params
    // =========================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e) {

        List<ValidationViolation> violations = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> new ValidationViolation(
                        err.getField(),
                        err.getDefaultMessage()
                ))
                .collect(Collectors.toList());

        log.error("MethodArgumentNotValidException: {}", e.getMessage(), e);

        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMissingParam(MissingServletRequestParameterException e) {

        log.error("MissingServletRequestParameterException: {}", e.getMessage(), e);

        return apiError(
                HttpStatus.BAD_REQUEST,
                e.getMessage()
        );
    }

    // =========================
    // 400 - business bad request
    // =========================
    @ExceptionHandler({
            ValidationException.class,
            IllegalArgumentException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequest(Exception e) {

        log.error("400 BAD_REQUEST: {}", e.getMessage(), e);

        return apiError(
                HttpStatus.BAD_REQUEST,
                e.getMessage()
        );
    }

    // =========================
    // 404
    // =========================
    @ExceptionHandler({
            CategoryNotFoundException.class,
            UserNotFoundException.class,
            EntityNotFoundException.class,
            OperationUnnecessaryException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(RuntimeException e) {

        log.error("404 NOT_FOUND: {}", e.getMessage(), e);

        return apiError(
                HttpStatus.NOT_FOUND,
                e.getMessage()
        );
    }

    // =========================
    // 403
    // =========================
    @ExceptionHandler(EntityUpdateException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleForbidden(EntityUpdateException e) {

        log.error("403 FORBIDDEN: {}", e.getMessage(), e);

        return apiError(
                HttpStatus.FORBIDDEN,
                e.getMessage()
        );
    }

    // =========================
    // 409
    // =========================
    @ExceptionHandler({
            ConditionNotMetException.class,
            DataIntegrityViolationException.class,
            NotPublishEventException.class,
            InitiatorRequestException.class,
            ParticipantLimitException.class,
            RepeatUserRequestorException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflict(Exception e) {

        log.error("409 CONFLICT: {}", e.getMessage(), e);

        return apiError(
                HttpStatus.CONFLICT,
                e.getMessage()
        );
    }

    // =========================
    // 500
    // =========================
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleUnexpected(Exception e) {

        log.error("500 INTERNAL_SERVER_ERROR: {}", e.getMessage(), e);

        return apiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error"
        );
    }

    // =========================
    // helper
    // =========================
    private ApiError apiError(HttpStatus status, String message) {
        return new ApiError(
                status.name(),
                status.getReasonPhrase(),
                message,
                LocalDateTime.now().toString()
        );
    }
}
