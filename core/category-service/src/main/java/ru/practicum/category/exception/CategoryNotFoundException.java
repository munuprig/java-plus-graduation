package ru.practicum.category.exception;

import ru.practicum.exception.NotFoundException;

public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
