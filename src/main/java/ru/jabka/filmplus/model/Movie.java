package ru.jabka.filmplus.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
public class Movie {
    private Long id;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private Long duration;
    private Genre genre;
}