package ru.practicum.exception;

public class ConditionNotMetException extends ConflictException {
    public ConditionNotMetException(String message) {
        super(message);
    }
}
