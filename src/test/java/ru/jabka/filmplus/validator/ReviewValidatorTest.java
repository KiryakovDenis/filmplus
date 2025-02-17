package ru.jabka.filmplus.validator;

import org.junit.jupiter.api.Test;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Review;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReviewValidatorTest {

    private final Review emptyReviewText = new Review(
            "",
            1L,
            1L,
            LocalDate.of(2020, 05, 15)
    );

    private final Review nullReviewText = new Review(
            null,
            1L,
            1L,
            LocalDate.of(2020, 05, 15)
    );

    private final Review nullUserId = new Review(
            "ReviewText",
            null,
            1L,
            LocalDate.of(2020, 05, 15)
    );

    private final Review nullMovieId = new Review(
            "ReviewText",
            1L,
            null,
            LocalDate.of(2020, 05, 15)
    );

    private final Review nullReviewDate = new Review(
            "ReviewText",
            1L,
            1L,
             null
    );

    @Test
    public void validateEmptyReviewText_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            ReviewValidator.validate(emptyReviewText);
        });
    }

    @Test
    public void validateNullReviewText_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            ReviewValidator.validate(nullReviewText);
        });
    }

    @Test
    public void validateNullUserIdText_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            ReviewValidator.validate(nullUserId);
        });
    }

    @Test
    public void validateNullMovieIdText_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            ReviewValidator.validate(nullMovieId);
        });
    }

    @Test
    public void validateReviewDateText_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            ReviewValidator.validate(nullReviewDate);
        });
    }
}