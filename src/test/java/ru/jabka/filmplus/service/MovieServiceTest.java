package ru.jabka.filmplus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabka.filmplus.exception.DataBaseException;
import ru.jabka.filmplus.model.Genre;
import ru.jabka.filmplus.model.Movie;
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
    @DisplayName("Ошибка базы данных при создании фильма")
    void createValidMovie_checkDataBaseException() {
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

}
