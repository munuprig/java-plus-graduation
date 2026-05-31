package ru.practicum.exception;

public class ValidationException extends BadRequestException {

    public ValidationException(String message) {
        super(message);
    }
    public ValidationException(Class<?> entityClass, String message) {
        super(entityClass.getSimpleName() + message);
    }
}