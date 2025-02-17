package ru.jabka.filmplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jabka.filmplus.model.Review;
import ru.jabka.filmplus.repository.ReviewRepository;
import ru.jabka.filmplus.validator.ReviewValidator;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public void create(Review review) {
        ReviewValidator.validate(review);
        reviewRepository.insert(review);
    }
}
