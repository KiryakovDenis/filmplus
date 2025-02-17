package ru.jabka.filmplus.model;

import java.time.LocalDate;

public record Review(String reviewText, Long userId, Long movieId, LocalDate reviewDate) {
}
