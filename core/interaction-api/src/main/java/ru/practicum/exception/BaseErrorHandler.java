package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice
public abstract class BaseErrorHandler {
    public record ErrorResponse(String message) {
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Throwable.class})
    public ErrorResponse handleAnyException(final Exception e) {
        log.error("{} - {}", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        final List<ValidationViolation> validationViolations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> {
                            log.error("MethodArgumentNotValidException: {} : {}", error.getField(), error.getDefaultMessage());
                            return new ValidationViolation(error.getField(), error.getDefaultMessage());
                        }
                )
                .collect(Collectors.toList());

        return new ValidationErrorResponse(validationViolations);
    }


    @ExceptionHandler({EntityNotFoundException.class, OperationUnnecessaryException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError onEntityNotFoundException(final EntityNotFoundException e) {
        log.error("EntityNotFoundException - 404: {}", e.getMessage(), e);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();
        return new ApiError("NOT_FOUND", "entity not found", stackTrace, LocalDateTime.now().toString());
    }

    @ExceptionHandler({EntityUpdateException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError onEntityUpdateException(final EntityUpdateException e) {
        log.error("EntityUpdateException - 409: {}", e.getMessage(), e);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();
        return new ApiError("FORBIDDEN", "entity update forbidden", stackTrace, LocalDateTime.now().toString());
    }

    @ExceptionHandler({ConditionNotMetException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError onConditionNotMetException(final ConditionNotMetException e) {
        log.error("ConditionNotMetException - 409: {}", e.getMessage(), e);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();
        return new ApiError(
                "CONFLICT",
                "For the requested operation the conditions are not met.",
                stackTrace,
                LocalDateTime.now().toString()
        );
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleAnyException(final Throwable e) {
        log.error("Error:500; {}", e.getMessage(), e);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();

        return new ApiError("INTERNAL_SERVER_ERROR", "internal server error", stackTrace, LocalDateTime.now().toString());
    }

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError validationException(final ValidationException e) {
        log.error("ValidationException - 400: {}", e.getMessage(), e);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();
        return new ApiError("BAD_REQUEST", "validation exception", stackTrace, LocalDateTime.now().toString());
    }

    @ExceptionHandler({NotPublishEventException.class,
            InitiatorRequestException.class,
            ParticipantLimitException.class,
            RepeatUserRequestorException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError onNotPublishEventException(final RuntimeException e) {
        log.error("409: {}", e.getMessage(), e);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();
        return new ApiError("CONFLICT", "event is not published", stackTrace, LocalDateTime.now().toString());
    }

    public record ApiError(String status, String reason, String message, String timestamp) {
    }

    @ExceptionHandler({EntityNotFoundException.class,
            OperationUnnecessaryException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final Exception e) {
        log.error("{} - {}", HttpStatus.NOT_FOUND, e.getMessage(), e);
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            IllegalArgumentException.class,
            MethodArgumentNotValidException.class,
            ValidationException.class,
            MissingServletRequestParameterException.class
    })
    public ErrorResponse handleIBadRequestException(final Exception e) {
        log.error("{} - {}", HttpStatus.BAD_REQUEST, e.getMessage(), e);
        return new ErrorResponse(e.getMessage());
    }
}
