package ru.practicum.request.exception;

import ru.practicum.exception.BadRequestException;

public class ValidationException extends BadRequestException {

    public ValidationException(String message) {
        super(message);
    }
}