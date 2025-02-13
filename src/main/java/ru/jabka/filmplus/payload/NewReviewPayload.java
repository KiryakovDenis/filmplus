package ru.jabka.filmplus.payload;

public class NewReviewPayload {
    Long userId;
    Long movieId;
    String message;

    public NewReviewPayload(Long userId, Long filmId, String review) {
        this.userId = userId;
        this.movieId = filmId;
        this.message = review;
    }

    public NewReviewPayload() {}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}