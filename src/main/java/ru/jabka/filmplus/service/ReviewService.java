package ru.jabka.filmplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Review;
import ru.jabka.filmplus.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional(rollbackFor = Exception.class)
    public void create(final Review review) {
        validate(review);
        reviewRepository.insert(review);
    }

    private void validate(Review review) {
        if (!StringUtils.hasText(review.getReviewText())) {
            throw new BadRequestException("Заполните текст отзыва к фильму");
        }
        if (review.getUserId() == null) {
            throw new BadRequestException("Укажите идентификатор пользователя");
        }
        if (review.getMovieId() == null) {
            throw new BadRequestException("Укажите идентификатор фильма");
        }

        if (review.getReviewDate() == null) {
            throw new BadRequestException("Заполните дату отзыва");
        }
    };
}