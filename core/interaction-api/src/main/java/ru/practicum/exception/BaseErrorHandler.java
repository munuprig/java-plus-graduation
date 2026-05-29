package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
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
public class BaseErrorHandler {

    public record ApiError(
            String status,
            String reason,
            String message,
            String timestamp
    ) {}

    public record ValidationViolation(String field, String message) {}

    public record ValidationErrorResponse(List<ValidationViolation> violations) {}

    // 400

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorResponse handleValidation(MethodArgumentNotValidException e) {

        List<ValidationViolation> violations = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> {
                    log.error("Validation error: {} - {}", err.getField(), err.getDefaultMessage());
                    return new ValidationViolation(err.getField(), err.getDefaultMessage());
                })
                .collect(Collectors.toList());

        return new ValidationErrorResponse(violations);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiError handleMissingParam(MissingServletRequestParameterException e) {
        log.error("Missing parameter: {}", e.getMessage(), e);
        return buildError(HttpStatus.BAD_REQUEST, "BAD_REQUEST", e.getMessage(), e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            IllegalArgumentException.class,
            ValidationException.class
    })
    public ApiError handleBadRequest(RuntimeException e) {
        log.error("Bad request: {}", e.getMessage(), e);
        return buildError(HttpStatus.BAD_REQUEST, "BAD_REQUEST", e.getMessage(), e);
    }

    // 404

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            EntityNotFoundException.class,
            OperationUnnecessaryException.class
    })
    public ApiError handleNotFound(RuntimeException e) {
        log.error("Not found: {}", e.getMessage(), e);
        return buildError(HttpStatus.NOT_FOUND, "NOT_FOUND", e.getMessage(), e);
    }

    // 409

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({
            ConditionNotMetException.class,
            NotPublishEventException.class,
            InitiatorRequestException.class,
            ParticipantLimitException.class,
            RepeatUserRequestorException.class
    })
    public ApiError handleConflict(RuntimeException e) {
        log.error("Conflict: {}", e.getMessage(), e);
        return buildError(HttpStatus.CONFLICT, "CONFLICT", e.getMessage(), e);
    }

    // 500

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiError handleAny(Exception e) {
        log.error("Internal error: {}", e.getMessage(), e);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_SERVER_ERROR",
                e.getMessage(),
                e);
    }

    private ApiError buildError(HttpStatus status, String reason, String message, Exception e) {
        return new ApiError(
                status.name(),
                reason,
                message,
                LocalDateTime.now().toString()
        );
    }
}