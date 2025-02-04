package ru.jabka.filmplus.model;

public enum Genre {
    COMEDY ("Комедия"),
    ACTION ("Боевик"),
    MELODRAMA ("Мелодрама"),
    DOCUMENTARY ("Документальное кино"),
    DETECTIVE ("Детектив"),
    HISTORY ("Историческое кино");

    private String name;

    Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}