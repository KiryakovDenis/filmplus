package ru.jabka.filmplus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Review;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

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

    @InjectMocks
    private ReviewService reviewService;

    @Test
    @DisplayName("Валидация пустого текста отзыва")
    public void validateEmptyReviewText_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            reviewService.create(emptyReviewText);
        });
    }

    @Test
    @DisplayName("Валидация текста отзыва на null значение")
    public void validateNullReviewText_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            reviewService.create(nullReviewText);
        });
    }

    @Test
    @DisplayName("Валидация незаполненного userId")
    public void validateNullUserIdText_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            reviewService.create(nullUserId);
        });
    }

    @Test
    @DisplayName("Валидация незаполненного MoiveId")
    public void validateNullMovieIdText_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            reviewService.create(nullMovieId);
        });
    }

    @Test
    @DisplayName("Валидация незаполненной даты отзыва")
    public void validateReviewDateText_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            reviewService.create(nullReviewDate);
        });
    }
}