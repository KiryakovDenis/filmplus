package ru.jabka.filmplus.validator;

import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Movie;

public class MovieValidator {
    public static void validate(Movie movie) {
        if (!StringUtils.hasText(movie.getTitle())) {
            throw new BadRequestException("Заполните наименование фильма");
        }
        if (movie.getDuration() == null) {
            throw new BadRequestException("Укажите длительность фильма");
        }
        if (!StringUtils.hasText(movie.getDescription())) {
            throw new BadRequestException("Заполните описание фильма");
        }
        if (movie.getGenre() == null) {
            throw new BadRequestException("Укажите жанр");
        }
        if (movie.getReleaseDate() == null) {
            throw new BadRequestException("Заполните дату выхода фильма");
        }
    }
}
