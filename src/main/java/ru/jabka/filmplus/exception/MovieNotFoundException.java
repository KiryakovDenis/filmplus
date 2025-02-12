package ru.jabka.filmplus.exception;

import org.apache.logging.log4j.util.Supplier;

public class MovieNotFoundException extends RuntimeException{

    public static Supplier<MovieNotFoundException> create(Long id) {
        return () -> new MovieNotFoundException(id);
    }

    public MovieNotFoundException(Long id) {
        super(String.format("Фильм не найден {id = %s}", id));
    }
}