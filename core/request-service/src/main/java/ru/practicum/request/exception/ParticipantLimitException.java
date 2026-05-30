package ru.practicum.request.exception;

import ru.practicum.exception.ConflictException;

public class ParticipantLimitException extends ConflictException {

    public ParticipantLimitException(String message) {
        super(message);
    }
}