package ru.jabka.filmplus.service;

import org.springframework.stereotype.Service;
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

    public Film getById(Long id) {
        return this.films
                .stream().filter(f -> f.getId().equals(id))
                .findFirst()
                .orElseThrow(FilmNotFoundException.create(id));
    }

    public Film update(final UpdateFilmPayload film) {
        Film existFilm = getById(film.getId());

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
        this.films.remove(getById(id));
    }

    private void validate(Film film) {
        if (film.getName() == null) {
            throw new BadRequestException("Заполните наименование фильма");
        }
        if (film.getDuration() == null) {
            throw new BadRequestException("Укажите длительность фильма");
        }
        if (film.getDescription() == null) {
            throw new BadRequestException("Заполните описание фильма");
        }
        if (film.getGenres() == null) {
            throw new BadRequestException("Укажите жанр");
        }
        if (film.getReleaseDate() == null) {
            throw new BadRequestException("Заполните дату выхода фильма");
        }
        if (film.getId() == null) {
            throw new BadRequestException("Не указан ID");
        }
    }

    public List<Film> findFilmByPeriodGenreName(LocalDate beginDate,
                                                LocalDate endDate,
                                                Genre genre,
                                                String name) {
        return this.films.stream()
                .filter(f -> (
                                //Жанр заполнен или нет
                                ((genre == null) || f.getGenres().equals(genre)) &&
                                //Имя заполнено, или нет
                                ((name == null) || f.getName().equals(name)) &&
                                //Заполнены обе даты или только одна из них
                                (
                                    (beginDate == null && endDate == null) ||
                                    (
                                            (beginDate != null && endDate == null) &&
                                            (f.getReleaseDate().isAfter(beginDate))
                                    ) ||
                                    (
                                            (beginDate == null && endDate != null) &&
                                            (f.getReleaseDate().isBefore(endDate))
                                    )||
                                    (
                                            (beginDate != null && endDate != null) &&
                                            (f.getReleaseDate().isBefore(endDate)) &&
                                            (f.getReleaseDate().isAfter(beginDate))
                                    )
                                )
                            )
                ).toList();
    }

    public void like(final Long filmId, Long userId) {
        this.getById(filmId).like(userId);
    }

    public void dislike(final Long filmId, final Long userId) {
        this.getById(filmId).dislike(userId);
    }

    public void review(NewReviewPayload review) {
        this.getById(review.getFilmId()).addReview(new Review(review.getMessage(), review.getUserId()));
    }

    public List<Review> getReviews(final Long filmId) {
        return getById(filmId).getReviews();
    }
}
