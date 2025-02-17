package ru.jabka.filmplus.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Genre;
import ru.jabka.filmplus.model.Movie;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MovieValidatorTest {

    private Movie emptyTitleMovie = Movie.builder()
            .id(1L)
            .genre(Genre.ACTION)
            .releaseDate(LocalDate.of(2024, 1, 15))
            .description("test")
            .duration(4L)
            .build();

    private Movie emptyGenreMovie = Movie.builder()
            .id(1L)
            .title("test")
            .releaseDate(LocalDate.of(2024, 1, 15))
            .description("test")
            .duration(4L)
            .build();

    private Movie emptyReleaseDateMovie = Movie.builder()
            .id(1L)
            .title("test")
            .genre(Genre.ACTION)
            .description("test")
            .duration(4L)
            .build();

    private Movie emptyDescriptionMovie = Movie.builder()
            .id(1L)
            .title("test")
            .genre(Genre.ACTION)
            .releaseDate(LocalDate.of(2024, 1, 15))
            .duration(4L)
            .build();

    private Movie emptyDurationMovie = Movie.builder()
            .id(1L)
            .title("test")
            .genre(Genre.ACTION)
            .releaseDate(LocalDate.of(2024, 1, 15))
            .description("test")
            .build();


    @Test
    void createEmptyTitle_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            MovieValidator.validate(emptyTitleMovie);
        });
    }

    @Test
    void createEmptyGenre_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            MovieValidator.validate(emptyGenreMovie);
        });
    }

    @Test
    void createReleaseDate_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            MovieValidator.validate(emptyReleaseDateMovie);
        });
    }

    @Test
    void createDescription_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            MovieValidator.validate(emptyDescriptionMovie);
        });
    }

    @Test
    void createDuration_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            MovieValidator.validate(emptyDurationMovie);
        });
    }
}