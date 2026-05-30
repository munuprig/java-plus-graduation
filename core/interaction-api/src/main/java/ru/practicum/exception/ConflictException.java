package ru.practicum.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(Class<?> entityClass, String message) {
        super(entityClass.getSimpleName() + message);}
}
