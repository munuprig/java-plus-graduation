package ru.practicum.exception;

public record ValidationViolation(
        String field,
        String message
) {}