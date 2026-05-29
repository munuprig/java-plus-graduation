package ru.practicum.request.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.exception.*;

@Slf4j
@RestControllerAdvice
public class ErrorHandlerRequest extends BaseErrorHandler {

    @ExceptionHandler({
            IllegalArgumentException.class,
            MethodArgumentNotValidException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError badRequest(Exception e) {
        log.error("400 BAD_REQUEST: {}", e.getMessage(), e);
        return buildError(HttpStatus.BAD_REQUEST, "BAD_REQUEST", e);
    }

    @ExceptionHandler({
            InitiatorRequestException.class,
            ParticipantLimitException.class,
            RepeatUserRequestorException.class,
            NotPublishEventException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError conflict(Exception e) {
        log.error("409 CONFLICT: {}", e.getMessage(), e);
        return buildError(HttpStatus.CONFLICT, "CONFLICT", e);
    }
}