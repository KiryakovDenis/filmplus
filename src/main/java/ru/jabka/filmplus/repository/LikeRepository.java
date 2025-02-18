package ru.jabka.filmplus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabka.filmplus.exception.DataBaseException;
import ru.jabka.filmplus.exception.NoDataFoundException;
import ru.jabka.filmplus.model.Like;

@Repository
@RequiredArgsConstructor
public class LikeRepository {
    private final String INSERT = """
            INSERT INTO filmplus."like" (user_id, movie_id) VALUES(:user_id, :movie_id)
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public void insert(Like like) {
        try {
            jdbcTemplate.update(INSERT, likePayloadToSql(like));
        } catch (DataIntegrityViolationException e) {
            handleDbExceptionMessage(e.getMessage(), like);
        } catch (Exception e) {
            throw DataBaseException.create(e.getMessage());
        }
    }

    private MapSqlParameterSource likePayloadToSql(Like like) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("user_id", like.getUserId());
        params.addValue("movie_id", like.getMovieId());

        return params;
    }

    private void handleDbExceptionMessage(String message, Like like) {
        if (message.contains("like_user_fk")) {
            throw NoDataFoundException.create(String.format("Пользователь не найден [id = %s]", like.getUserId()));
        } else if (message.contains("like_movie_fk")) {
            throw NoDataFoundException.create(String.format("Фильм не найден [id = %s]", like.getMovieId()));
        } else if (message.contains("like_pk")) {
            throw NoDataFoundException.create(String.format("Пользователь уже лайкнул фильм ранее [userId = %s; movieId = %s]", like.getUserId(), like.getMovieId()));
        } else {
            throw DataBaseException.create(message);
        }
    }
}