package ru.jabka.filmplus.model;

import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class Movie {
    private final Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Long duration;
    private Genre genres;
    private Set<Long> likes = new HashSet<>();
    private List<Review> reviews = new ArrayList<>();

    public Movie(Long id, String name, String description, LocalDate releaseDate, Long duration, Genre genres) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                ", genres=" + genres +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Long getDuration() {
        return duration;
    }

    public Genre getGenres() {
        return genres;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public void setGenres(Genre genres) {
        this.genres = genres;
    }

    public void like(Long userId){
        this.likes.add(userId);
    }
    public Integer getLikes() {
        return this.likes.size();
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public List<Review> getReviews() {
        return this.reviews;
    }
}