package ru.jabka.filmplus.exception;

import org.apache.logging.log4j.util.Supplier;

public class FilmNotFoundException extends RuntimeException{

    public static Supplier<FilmNotFoundException> create(Long id) {
        return () -> new FilmNotFoundException(id);
    }

    public FilmNotFoundException (Long id) {
        super(String.format("Фильм не найден {id = %s}", id));
    }
}
