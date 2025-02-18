package ru.jabka.filmplus.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String email;
    private String login;
    private LocalDate birthday;
}