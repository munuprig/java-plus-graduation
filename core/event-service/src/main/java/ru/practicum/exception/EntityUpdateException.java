package ru.practicum.exception;

public class EntityUpdateException extends ConflictException  {
    public EntityUpdateException(String message) {
        super(message);
    }
}
