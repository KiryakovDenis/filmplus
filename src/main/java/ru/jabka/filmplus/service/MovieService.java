package ru.jabka.filmplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Movie;
import ru.jabka.filmplus.payload.SearchMoviePayload;
import ru.jabka.filmplus.repository.MovieRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    @Transactional(rollbackFor = Exception.class)
    public Movie create(Movie movie) {
        validate(movie);
        return movieRepository.insert(movie);
    }

    @Transactional(rollbackFor = Exception.class)
    public Movie update(final Movie movie) {
        validate(movie);
        return movieRepository.update(movie);
    }

    @Transactional(readOnly = true)
    public List<Movie> search(SearchMoviePayload search) {
        searchValidate(search);
        return movieRepository.search(search);
    }

    private void validate(Movie movie) {
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

    private void searchValidate(SearchMoviePayload search) {
        if (search.getBeginDate() != null &&
                search.getEndDate() != null &&
                search.getBeginDate().isAfter(search.getEndDate())) {
            throw BadRequestException.create("Дата начала должна быть больше даты окончания");
        }
    }
}