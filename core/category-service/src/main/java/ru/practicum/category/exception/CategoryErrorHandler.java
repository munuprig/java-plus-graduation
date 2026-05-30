package ru.practicum.category.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.exception.ApiError;
import ru.practicum.exception.BaseErrorHandler;

@Slf4j
@RestControllerAdvice
public class CategoryErrorHandler extends BaseErrorHandler {

    @ExceptionHandler(CategoryNotFoundException.class)
    public ApiError handle(CategoryNotFoundException e) {
        return build(
                org.springframework.http.HttpStatus.NOT_FOUND,
                "category not found",
                e
        );
    }
}