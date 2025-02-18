package ru.jabka.filmplus.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.jabka.filmplus.model.Movie;
import ru.jabka.filmplus.model.Genre;
import ru.jabka.filmplus.payload.SearchMoviePayload;
import ru.jabka.filmplus.service.MovieService;
import ru.jabka.filmplus.service.UserService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
@Tag(name = "Фильмы")
@RequiredArgsConstructor
public class MovieController {

    public final MovieService movieService;
    public final UserService userService;

    @PostMapping
    @Operation(summary = "Создать фильм")
    public Movie create(@RequestBody final Movie movie) {
        return movieService.create(movie);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Найти фильм по идентификатору")
    public Movie get(@PathVariable final Long id) {
        return movieService.getById(id);
    }

    @PatchMapping()
    @Operation(summary = "Обновление информации о фильме")
    public Movie update(@RequestBody final Movie movie) {
        return movieService.update(movie);
    }

    @GetMapping
    @Operation(summary = "Поиск фильма по жанру, названию, или периода по дате выхода")
    public List<Movie> search(@RequestParam(required = false, name="id")
                              @Parameter(description = "Идентификатор фильма") Long id,
                              @RequestParam(required = false, name="begin_date")
                              @JsonFormat(pattern = "yyyy-MM-dd")
                              @Parameter(description = "Дата начала периода поиска фильмов") LocalDate beginDate,
                              @RequestParam(required = false, name="end_date")
                              @JsonFormat(pattern = "yyyy-MM-dd")
                              @Parameter(description = "Дата окончания периода поиска фильмов") LocalDate endDate,
                              @RequestParam(required = false, name="genre")
                              @Parameter(description = "Жанр фильма") Genre genre,
                              @RequestParam(required = false, name="title")
                              @Parameter(description = "Наименование фильма") String title,
                              @RequestParam(required = false, name="duration")
                              @Parameter(description = "Длительность фильма") Long duration) {
        SearchMoviePayload searchParam = new SearchMoviePayload(beginDate, endDate, title, duration, genre);

        return movieService.search(searchParam);
    }
}