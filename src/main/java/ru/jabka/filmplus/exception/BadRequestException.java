package ru.jabka.filmplus.exception;

public class BadRequestException extends RuntimeException {
    public static BadRequestException create(final String message){
        return new BadRequestException(message);
    }
    public BadRequestException(final String message) {
        super(message);
    }
}