package ru.jabka.filmplus.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.jabka.filmplus.model.Film;
import ru.jabka.filmplus.model.Genre;
import ru.jabka.filmplus.model.Review;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.payload.NewFilmPayload;
import ru.jabka.filmplus.payload.NewReviewPayload;
import ru.jabka.filmplus.payload.UpdateFilmPayload;
import ru.jabka.filmplus.service.FilmService;
import ru.jabka.filmplus.service.UserService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/film")
@Tag(name = "Фильмы")
public class FilmController {

    public final FilmService filmService;
    public final UserService userService;

    public FilmController(FilmService filmService, UserService userService) {
        this.filmService = filmService;
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Создать фильм")
    public Film create(@RequestBody final NewFilmPayload film) {
        return this.filmService.create(film);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Найти фильм по идентификатору")
    public Film get(@PathVariable final Long id) {
        return this.filmService.getById(id);
    }

    @PatchMapping()
    @Operation(summary = "Обновление информации о фильме")
    public Film update(@RequestBody final UpdateFilmPayload film) {
        return this.filmService.update(film);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление фильма")
    public void delete(@PathVariable final Long id) {
        this.filmService.delete(id);
    }

    @GetMapping
    @Operation(summary = "Поиск фильма по жанру, названию, или периода по дате выхода")
    public List<Film> findFilm(@RequestParam(required = false, name="begin_date")
                               @JsonFormat(pattern = "yyyy-MM-dd")
                               @Parameter(description = "Дата начала периода поиска фильмов") LocalDate beginDate,
                               @RequestParam(required = false, name="end_date")
                               @JsonFormat(pattern = "yyyy-MM-dd")
                               @Parameter(description = "Дата окончания периода поиска фильмов") LocalDate endDate,
                               @RequestParam(required = false, name="genre")
                               @Parameter(description = "Жанр фильма") Genre genre,
                               @RequestParam(required = false, name="name")
                               @Parameter(description = "Наименование фильма") String name) {
        return this.filmService.findFilmByPeriodGenreName(beginDate, endDate, genre, name);
    }

    @PostMapping("/{id}/like/{userId}")
    @Operation(summary = "Лайк фильму")
    public void like(@PathVariable(name = "id") Long id,
                     @PathVariable(name = "userId") Long userId) {
        User existUser = userService.getById(userId);
        this.filmService.like(id, userId);
    }

    @PostMapping("/{id}/dislike/{userId}")
    @Operation(summary = "Дизлайк фильму")
    public void dislike(@PathVariable(name = "id") Long id,
                        @PathVariable(name = "userId") Long userId) {
        User existUser = userService.getById(userId);
        this.filmService.dislike(id, userId);
    }

    @PostMapping("/{id}/review")
    @Operation(summary = "Отзыв на фильм")
    public void review(@RequestBody final NewReviewPayload review) {
        User existUser = userService.getById(review.getUserId());
        this.filmService.review(review);
    }

    @GetMapping("/{id}/review")
    @Operation(summary = "Список отзывов на фильм")
    public List<Review> getReview(@PathVariable(name = "id") final Long id) {
        return this.filmService.getReviews(id);
    }
}
