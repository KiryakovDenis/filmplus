package ru.jabka.filmplus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.exception.DataBaseException;
import ru.jabka.filmplus.exception.NoDataFoundException;
import ru.jabka.filmplus.model.Genre;
import ru.jabka.filmplus.model.Movie;
import ru.jabka.filmplus.payload.SearchMoviePayload;
import ru.jabka.filmplus.repository.MovieRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private Movie validMovie = Movie.builder()
            .id(1L)
            .title("Хорошее кино")
            .description("Очень хорошее кино")
            .genre(Genre.DETECTIVE)
            .duration(2L)
            .releaseDate(LocalDate.of(1987, 5, 22))
            .build();

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

    SearchMoviePayload badPeriodPayload = new SearchMoviePayload(
            LocalDate.of(2015, 04, 24),
            LocalDate.of(2012, 01, 05),
            "Good film",
            2L,
            Genre.DETECTIVE
    );

    @Test
    @DisplayName("Успешное создание фильма")
    public void createValidMovie() {
        Mockito.when(movieRepository.insert(validMovie)).thenReturn(validMovie);

        Movie result = movieService.create(validMovie);

        assertThat(result).isEqualTo(validMovie);

        verify(movieRepository).insert(validMovie);
    }

    @Test
    @DisplayName("Успешное обновление фильма")
    public void updateValidMovie() {
        Mockito.when(movieRepository.update(validMovie)).thenReturn(validMovie);

        Movie result = movieService.update(validMovie);

        assertThat(result).isEqualTo(validMovie);

        verify(movieRepository).update(validMovie);
    }

    @Test
    @DisplayName("Успешный поиск фильма по идентификатору")
    public void getById_success() {
        Mockito.when(movieRepository.getById(1L)).thenReturn(validMovie);

        Movie result = movieService.getById(1L);

        assertThat(result).isEqualTo(validMovie);

        verify(movieRepository).getById(1L);
    }

    @Test
    @DisplayName("Неудачный поиск фильма по идентификатору")
    public void getById_DataNotFoundException() {
        Mockito.when(movieRepository.getById(2L)).thenThrow(NoDataFoundException.class);

        NoDataFoundException ndfException = assertThrows(NoDataFoundException.class, () -> {
           movieService.getById(2L);
        });

        verify(movieRepository).getById(2L);
    }

    @Test
    @DisplayName("Ошибка базы данных при получении фильма по id")
    public void getById_BadRequestException() {
        Mockito.when(movieRepository.getById(2L)).thenThrow(BadRequestException.class);

        BadRequestException ndfException = assertThrows(BadRequestException.class, () -> {
            movieService.getById(2L);
        });

        verify(movieRepository).getById(2L);
    }

    @Test
    @DisplayName("Ошибка базы данных при создании фильма")
    public void createValidMovie_checkDataBaseException() {
        Mockito.when(movieRepository.insert(validMovie)).thenThrow(DataBaseException.class);

        DataBaseException dbException = assertThrows(DataBaseException.class, () -> {
            movieService.create(validMovie);
        });

        verify(movieRepository).insert(validMovie);
    }

    @Test
    @DisplayName("Ошибка базы данных при обновлении фильма")
    void updateValidMovie_checkDataBaseException() {
        Mockito.when(movieRepository.update(validMovie)).thenThrow(DataBaseException.class);

        DataBaseException dbException = assertThrows(DataBaseException.class, () -> {
            movieService.update(validMovie);
        });

        verify(movieRepository).update(validMovie);
    }

    @Test
    @DisplayName("Валидация пустого заголовка")
    void createEmptyTitle_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            movieService.create(emptyTitleMovie);
        });
    }

    @Test
    @DisplayName("Валидация пустого жанра")
    void createEmptyGenre_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            movieService.create(emptyGenreMovie);
        });
    }

    @Test
    @DisplayName("Валидация незаполненной даты выхода фильма")
    void createReleaseDate_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            movieService.create(emptyReleaseDateMovie);
        });
    }

    @Test
    @DisplayName("Валидация пустого описания фильма")
    void createDescription_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            movieService.create(emptyDescriptionMovie);
        });
    }

    @Test
    @DisplayName("Валидация незаполненной длительности фильма")
    void createDuration_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            movieService.create(emptyDurationMovie);
        });
    }

    @Test
    @DisplayName("Валидация неверно заданного периода при поиске фильмов")
    public void validateBadPeriodPayload_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            movieService.search(badPeriodPayload);
        });
    }
}