package ru.practicum.request.exception;

import ru.practicum.exception.ConflictException;

public class RepeatUserRequestorException extends ConflictException {

    public RepeatUserRequestorException(String message) {
        super(message);
    }
}