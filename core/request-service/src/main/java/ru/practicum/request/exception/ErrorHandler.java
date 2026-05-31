package ru.practicum.request.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.exception.BaseErrorHandler;
import ru.practicum.exception.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class ErrorHandler extends BaseErrorHandler {
    @ExceptionHandler({DataIntegrityViolationException.class,
            InitiatorRequestException.class,
            ParticipantLimitException.class,
            RepeatUserRequestorException.class,
            NotPublishEventException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflictException(final Exception e) {
        log.error("{} - {}", HttpStatus.CONFLICT, e.getMessage(), e);
        return new ErrorResponse(e.getMessage());
    }
}