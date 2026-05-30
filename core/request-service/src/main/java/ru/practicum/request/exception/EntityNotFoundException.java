package ru.practicum.request.exception;

import ru.practicum.exception.NotFoundException;

public class EntityNotFoundException extends NotFoundException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
