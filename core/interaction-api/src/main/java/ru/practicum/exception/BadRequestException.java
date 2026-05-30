package ru.practicum.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(Class<?> entityClass, String message) {
        super(entityClass.getSimpleName() + message);}
}
