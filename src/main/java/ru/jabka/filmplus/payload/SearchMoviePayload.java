package ru.jabka.filmplus.payload;

import ru.jabka.filmplus.model.Genre;

import java.time.LocalDate;

public record SearchMoviePayload(Long id,
                                 LocalDate beginDate,
                                 LocalDate endDate,
                                 String title,
                                 Long duration,
                                 Genre genre) {
}
