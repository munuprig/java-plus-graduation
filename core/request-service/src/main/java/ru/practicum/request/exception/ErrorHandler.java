package ru.practicum.request.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.exception.*;

@Slf4j
@RestControllerAdvice
public class ErrorHandler extends BaseErrorHandler {

}