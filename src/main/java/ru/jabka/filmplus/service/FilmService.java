package ru.jabka.filmplus.service;

import jakarta.validation.constraints.Null;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.exception.FilmNotFoundException;
import ru.jabka.filmplus.model.Film;
import ru.jabka.filmplus.model.Genre;
import ru.jabka.filmplus.model.Review;
import ru.jabka.filmplus.payload.NewFilmPayload;
import ru.jabka.filmplus.payload.NewReviewPayload;
import ru.jabka.filmplus.payload.UpdateFilmPayload;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FilmService {

    private static final Set<Film> films = new HashSet<>();

    public Film create(NewFilmPayload film) {
        Film newFilm =  new Film((long)films.size() + 1,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getGenres()
        );

        validate(newFilm);

        films.add(newFilm);
        return newFilm;
    }

    public Optional<Film> getById(Long id) {
        return this.films
                .stream().filter(f -> f.getId().equals(id))
                .findFirst();
    }

    public Film update(final UpdateFilmPayload film) {
        Film existFilm = getById(film.getId()).orElse(null);

        if (existFilm == null) {
            return null;
        }

        existFilm.setName(film.getName());
        existFilm.setDescription(film.getDescription());
        existFilm.setDuration(film.getDuration());
        existFilm.setGenres(film.getGenres());
        existFilm.setReleaseDate(film.getReleaseDate());

        validate(existFilm);

        return existFilm;

    }

    public void delete(final long id) {
        this.films.remove(getById(id).orElseThrow(FilmNotFoundException.create(id)));
    }

        public List<Film> findFilmByPeriodGenreName(LocalDate beginDate,
                                                LocalDate endDate,
                                                Genre genre,
                                                String name) {
        return this.films.stream()
                .filter(f -> {
                            boolean genreMatches = genre == null || f.getGenres().equals(genre);
                            boolean nameMatches = !StringUtils.hasText(name) || f.getName().equals(name);
                            return genreMatches && nameMatches && dateMatches(f.getReleaseDate(), beginDate, endDate);
                        }
                ).toList();
    }

    public void like(final Long filmId, Long userId) {
        this.getById(filmId).orElseThrow(FilmNotFoundException.create(filmId)).like(userId);
    }

    public void dislike(final Long filmId, final Long userId) {
        this.getById(filmId).orElseThrow(FilmNotFoundException.create(filmId)).dislike(userId);
    }

    public void review(NewReviewPayload review) {
        this.getById(review.getFilmId())
                .orElseThrow(FilmNotFoundException.create(review.getFilmId()))
                    .addReview(new Review(review.getMessage(), review.getUserId()));
    }

    public List<Review> getReviews(final Long filmId) {
        return getById(filmId).orElseThrow(FilmNotFoundException.create(filmId)).getReviews();
    }

    private void validate(Film film) {
        if (!StringUtils.hasText(film.getName())) {
            throw new BadRequestException("Заполните наименование фильма");
        }
        if (film.getDuration() == null) {
            throw new BadRequestException("Укажите длительность фильма");
        }
        if (!StringUtils.hasText(film.getDescription())) {
            throw new BadRequestException("Заполните описание фильма");
        }
        if (film.getGenres() == null) {
            throw new BadRequestException("Укажите жанр");
        }
        if (film.getReleaseDate() == null) {
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