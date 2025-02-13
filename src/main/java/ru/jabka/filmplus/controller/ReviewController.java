package ru.jabka.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabka.filmplus.payload.NewReviewPayload;
import ru.jabka.filmplus.service.MovieService;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final MovieService movieService;

    public ReviewController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    @Operation(summary = "Отзыв на фильм")
    public void review(@RequestBody final NewReviewPayload review) {
        movieService.review(review);
    }
}