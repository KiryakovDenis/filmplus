package ru.jabka.filmplus.validator;

import org.junit.jupiter.api.Test;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.Genre;
import ru.jabka.filmplus.payload.SearchMoviePayload;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SearchMoviePayloadValidatorTest {
    SearchMoviePayload badPeriodPayload = new SearchMoviePayload(
            1L,
            LocalDate.of(2015, 04, 24),
            LocalDate.of(2012, 01, 05),
            "Good film",
            2L,
            Genre.DETECTIVE
    );

    @Test
    public void validateBadPeriodPayload_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
           SearchMoviePayloadValidator.validate(badPeriodPayload);
        });
    }
}