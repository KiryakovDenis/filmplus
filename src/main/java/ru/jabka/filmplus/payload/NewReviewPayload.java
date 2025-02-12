package ru.jabka.filmplus.payload;

public class NewReviewPayload {
    Long userId;
    Long filmId;
    String message;

    public NewReviewPayload(Long userId, Long filmId, String review) {
        this.userId = userId;
        this.filmId = filmId;
        this.message = review;
    }

    public NewReviewPayload() {}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}