package ru.practicum.category.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.exception.BaseErrorHandler;

@Slf4j
@RestControllerAdvice
public class ErrorHandlerCategory extends BaseErrorHandler {

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFound(CategoryNotFoundException e) {
        log.error("404 CategoryNotFound: {}", e.getMessage(), e);
        return buildError(HttpStatus.NOT_FOUND, "NOT_FOUND", e);
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError conflict(org.springframework.dao.DataIntegrityViolationException e) {
        log.error("409 DataIntegrityViolation: {}", e.getMessage(), e);
        return buildError(HttpStatus.CONFLICT, "CONFLICT", e);
    }
}