package ru.jabka.filmplus.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.exception.MovieNotFoundException;
import ru.jabka.filmplus.model.Movie;
import ru.jabka.filmplus.model.Genre;
import ru.jabka.filmplus.model.Review;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.payload.LikePayload;
import ru.jabka.filmplus.payload.MoviePayload;
import ru.jabka.filmplus.payload.NewReviewPayload;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MovieService {

    private static final Set<Movie> MOVIES = new HashSet<>();
    private final UserService userService;

    public MovieService(UserService userService) {
        this.userService = userService;
    }

    public Movie create(MoviePayload film) {
        Movie newMovie =  new Movie((long) MOVIES.size() + 1,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getGenre()
        );

        validate(newMovie);

        MOVIES.add(newMovie);
        return newMovie;
    }

    public Movie getById(Long id) {
        return MOVIES
                .stream().filter(f -> f.getId().equals(id))
                .findFirst().orElseThrow(MovieNotFoundException.create(id));
    }

    public Movie update(final MoviePayload film) {

        Movie existMovie = getById(film.getId());

        existMovie.setName(film.getName());
        existMovie.setDescription(film.getDescription());
        existMovie.setDuration(film.getDuration());
        existMovie.setGenre(film.getGenre());
        existMovie.setReleaseDate(film.getReleaseDate());

        validate(existMovie);

        return existMovie;

    }

    public void delete(final long id) {
        MOVIES.remove(getById(id));
    }

    public List<Movie> search(LocalDate beginDate,
                              LocalDate endDate,
                              Genre genre,
                              String name) {
        return MOVIES.stream()
                .filter(f -> {
                            boolean genreMatches = genre == null || f.getGenre().equals(genre);
                            boolean nameMatches = !StringUtils.hasText(name) || f.getName().equals(name);
                            return genreMatches && nameMatches && dateMatches(f.getReleaseDate(), beginDate, endDate);
                        }
                ).toList();
    }

    public void like(final LikePayload like) {
        User existUser = userService.getById(like.getUserId());
        getById(like.getMovieId()).like(like.getUserId());
    }

    public void review(NewReviewPayload review) {
        User existUser = userService.getById(review.getUserId());
        getById(review.getMovieId()).addReview(new Review(review.getMessage(), review.getUserId()));
    }

    private void validate(Movie movie) {
        if (!StringUtils.hasText(movie.getName())) {
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

    private boolean dateMatches(LocalDate releaseDate, LocalDate beginDate, LocalDate endDate) {
        //Заданы обе даты периода
        boolean periodNotInitialized = (beginDate == null && endDate == null);
        //Задана только дата начала периода
        boolean endDateNotInitialized = (beginDate != null && endDate == null) && (releaseDate.isAfter(beginDate));
        //Задана только дата окончания периода
        boolean beginDateNotInitialized = (beginDate == null && endDate != null) && (releaseDate.isBefore(endDate));
        //Заданы обе даты периода
        boolean allDateInitialized = (beginDate != null && endDate != null) &&
                                        (releaseDate.isBefore(endDate)) &&
                                            (releaseDate.isAfter(beginDate));

        return periodNotInitialized ||
                endDateNotInitialized ||
                beginDateNotInitialized ||
                allDateInitialized;
    }
}