package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

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

    public record ValidationErrorResponse(java.util.List<ValidationViolation> violations) {}

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiError handleAnyException(Exception e) {
        log.error("500 ERROR: {}", e.getMessage(), e);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", e);
    }

    protected ApiError buildError(HttpStatus status, String reason, Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));

        return new ApiError(
                status.name(),
                reason,
                e.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}