package ru.practicum.request.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.exception.ApiError;
import ru.practicum.exception.BaseErrorHandler;

@Slf4j
@RestControllerAdvice
public class RequestErrorHandler extends BaseErrorHandler {

    @ExceptionHandler({
            EntityNotFoundException.class,
            OperationUnnecessaryException.class
    })
    public ApiError handleNotFound(RuntimeException e) {
        return build(
                org.springframework.http.HttpStatus.NOT_FOUND,
                "request not found",
                e
        );
    }

    @ExceptionHandler({
            InitiatorRequestException.class,
            ParticipantLimitException.class,
            RepeatUserRequestorException.class,
            NotPublishEventException.class
    })
    public ApiError handleConflict(RuntimeException e) {
        return build(
                org.springframework.http.HttpStatus.CONFLICT,
                "request conflict",
                e
        );
    }
}