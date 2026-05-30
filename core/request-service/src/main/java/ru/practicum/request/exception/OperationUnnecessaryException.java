package ru.practicum.request.exception;

import ru.practicum.exception.NotFoundException;

public class OperationUnnecessaryException extends NotFoundException {

    public OperationUnnecessaryException(String message) {
        super(message);
    }
}