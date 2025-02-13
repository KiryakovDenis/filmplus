package ru.jabka.filmplus.payload;

import ru.jabka.filmplus.model.Genre;
import java.time.LocalDate;

public class MoviePayload {
    private final Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Long duration;
    private Genre genre;

    public MoviePayload(Long id, String name, String description, LocalDate releaseDate, Long duration, Genre genre) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}