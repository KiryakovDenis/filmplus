package ru.jabka.filmplus.payload;

public class LikePayload {
    private Long userId;
    private Long MovieId;

    public LikePayload(Long userId, Long movieId) {
        this.userId = userId;
        MovieId = movieId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getMovieId() {
        return MovieId;
    }
}