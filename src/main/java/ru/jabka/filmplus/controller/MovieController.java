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
import ru.jabka.filmplus.model.Movie;
import ru.jabka.filmplus.model.Genre;
import ru.jabka.filmplus.payload.NewMoviePayload;
import ru.jabka.filmplus.payload.UpdateMoviePayload;
import ru.jabka.filmplus.service.MovieService;
import ru.jabka.filmplus.service.UserService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/film")
@Tag(name = "Фильмы")
public class MovieController {

    public final MovieService movieService;

    public MovieController(MovieService movieService, UserService userService) {
        this.movieService = movieService;
    }

    @PostMapping
    @Operation(summary = "Создать фильм")
    public Movie create(@RequestBody final NewMoviePayload film) {
        return this.movieService.create(film);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Найти фильм по идентификатору")
    public Movie get(@PathVariable final Long id) {
        return this.movieService.getById(id);
    }

    @PatchMapping()
    @Operation(summary = "Обновление информации о фильме")
    public Movie update(@RequestBody final UpdateMoviePayload film) {
        return this.movieService.update(film);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление фильма")
    public void delete(@PathVariable final Long id) {
        this.movieService.delete(id);
    }

    @GetMapping
    @Operation(summary = "Поиск фильма по жанру, названию, или периода по дате выхода")
    public List<Movie> search(@RequestParam(required = false, name="begin_date")
                               @JsonFormat(pattern = "yyyy-MM-dd")
                               @Parameter(description = "Дата начала периода поиска фильмов") LocalDate beginDate,
                              @RequestParam(required = false, name="end_date")
                               @JsonFormat(pattern = "yyyy-MM-dd")
                               @Parameter(description = "Дата окончания периода поиска фильмов") LocalDate endDate,
                              @RequestParam(required = false, name="genre")
                               @Parameter(description = "Жанр фильма") Genre genre,
                              @RequestParam(required = false, name="name")
                               @Parameter(description = "Наименование фильма") String name) {
        return this.movieService.search(beginDate, endDate, genre, name);
    }
}