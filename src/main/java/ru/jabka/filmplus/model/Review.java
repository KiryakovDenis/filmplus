package ru.jabka.filmplus.model;

import jakarta.validation.constraints.NotNull;

public class Review {
    @NotNull
    private final String message;
    @NotNull
    private final Long userId;

    public Review(String message, Long userId) {
        this.message = message;
        this.userId = userId;
    }

    public String getMessage() {
        return this.message;
    }

    public Long getUser() {
        return this.userId;
    }

}
