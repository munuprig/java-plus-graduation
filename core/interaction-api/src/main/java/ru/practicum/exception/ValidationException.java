package ru.practicum.exception;

public class ValidationException extends RuntimeException {

    public ValidationException(Class<?> entityClass, String message) {
        super(entityClass.getSimpleName() + message);
    }

    public ValidationException(String message) {
        super(message);
    }
}