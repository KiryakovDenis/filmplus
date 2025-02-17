package ru.jabka.filmplus.validator;

import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Review;

public class ReviewValidator {
    public static void validate(Review review) {

        if (!StringUtils.hasText(review.reviewText())) {
            throw new BadRequestException("Заполните текст отзыва к фильму");
        }
        if (review.userId() == null) {
            throw new BadRequestException("Укажите идентификатор пользователя");
        }
        if (review.movieId() == null) {
            throw new BadRequestException("Укажите идентификатор фильма");
        }

        if (review.reviewDate() == null) {
            throw new BadRequestException("Заполните дату отзыва");
        }
    };
}
