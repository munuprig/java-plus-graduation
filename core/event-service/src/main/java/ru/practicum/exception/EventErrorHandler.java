package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class EventErrorHandler extends BaseErrorHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ApiError handleNotFound(EventNotFoundException e) {
        return build(
                org.springframework.http.HttpStatus.NOT_FOUND,
                "event not found",
                e
        );
    }

    @ExceptionHandler(EntityUpdateException.class)
    public ApiError handleForbidden(EntityUpdateException e) {
        return build(
                org.springframework.http.HttpStatus.FORBIDDEN,
                "update forbidden",
                e
        );
    }

    @ExceptionHandler(ConditionNotMetException.class)
    public ApiError handleConflict(ConditionNotMetException e) {
        return build(
                org.springframework.http.HttpStatus.CONFLICT,
                "event conditions not met",
                e
        );
    }
}