package ru.jabka.filmplus.exception;

public class NoDataFoundException extends RuntimeException{
    public static NoDataFoundException create(String message) {
        return new NoDataFoundException(message);
    }
    public NoDataFoundException(final String message) {
        super(message);
    }
}
