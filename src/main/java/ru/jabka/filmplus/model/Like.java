package ru.jabka.filmplus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Like {
    private Long userId;
    private Long movieId;
};