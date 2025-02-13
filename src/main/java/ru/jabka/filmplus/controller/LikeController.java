package ru.jabka.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabka.filmplus.payload.LikePayload;
import ru.jabka.filmplus.service.MovieService;

@RestController
@RequestMapping("/api/v1/like")
public class LikeController {
    private final MovieService movieService;

    public LikeController(MovieService movieService) {
        this.movieService = movieService;    }

    @Operation(summary = "Лайк фильму")
    @PostMapping
    public void like(@RequestBody final LikePayload like) {
        movieService.like(like);
    }
}