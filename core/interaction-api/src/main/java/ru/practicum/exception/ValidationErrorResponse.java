package ru.practicum.exception;

import java.util.List;

public record ValidationErrorResponse(
        List<ValidationViolation> violations
) {}
