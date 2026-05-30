package ru.practicum.user.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.exception.ApiError;
import ru.practicum.exception.BaseErrorHandler;

@Slf4j
@RestControllerAdvice
public class UserErrorHandler extends BaseErrorHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ApiError handle(UserNotFoundException e) {
        return build(
                org.springframework.http.HttpStatus.NOT_FOUND,
                "user not found",
                e
        );
    }
}