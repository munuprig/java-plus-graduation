package ru.practicum.request.exception;

import ru.practicum.exception.ConflictException;

public class InitiatorRequestException extends ConflictException {

    public InitiatorRequestException(String message) {
        super(message);
    }
}