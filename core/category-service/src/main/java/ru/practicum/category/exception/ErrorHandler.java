package ru.practicum.category.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.exception.BaseErrorHandler;

@Slf4j
@RestControllerAdvice
public class ErrorHandler extends BaseErrorHandler {
//    @ExceptionHandler({DataIntegrityViolationException.class})
//    @ResponseStatus(HttpStatus.CONFLICT)
//    public ErrorResponse onDataIntegrityViolationException(final Exception e) {
//        log.error("{} - {}", HttpStatus.CONFLICT, e.getMessage(), e);
//        return new ErrorResponse(e.getMessage());
//    }
}