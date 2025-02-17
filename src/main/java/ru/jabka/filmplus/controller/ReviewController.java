package ru.jabka.filmplus.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabka.filmplus.model.Review;
import ru.jabka.filmplus.service.ReviewService;

@RestController
@RequestMapping("/api/v1/Review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public void create(@RequestBody final Review review) {
        reviewService.create(review);
    }
}
