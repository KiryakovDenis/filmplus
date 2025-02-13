package ru.jabka.filmplus.payload;

public class LikePayload {
    private Long userId;
    private Long movieId;

    public LikePayload(Long userId, Long movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getMovieId() {
        return movieId;
    }
}