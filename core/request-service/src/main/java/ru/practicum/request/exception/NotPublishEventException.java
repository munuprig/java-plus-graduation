package ru.practicum.request.exception;

import ru.practicum.exception.ConflictException;

public class NotPublishEventException extends ConflictException {

    public NotPublishEventException(String message) {
        super(message);
    }
}