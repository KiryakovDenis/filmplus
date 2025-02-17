package ru.jabka.filmplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Movie;
import ru.jabka.filmplus.payload.SearchMoviePayload;
import ru.jabka.filmplus.repository.MovieRepository;
import ru.jabka.filmplus.validator.MovieValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public Movie create(Movie movie) {
        MovieValidator.validate(movie);
        return movieRepository.insert(movie);
    }

    public Movie update(final Movie movie) {
        MovieValidator.validate(movie);
        return movieRepository.update(movie);
    }

    public List<Movie> search(SearchMoviePayload search) {
        return movieRepository.search(search);
    }
}