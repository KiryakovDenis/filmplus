package ru.jabka.filmplus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class Review {
    private String reviewText;
    private Long userId;
    private Long movieId;
    private LocalDate reviewDate;
}