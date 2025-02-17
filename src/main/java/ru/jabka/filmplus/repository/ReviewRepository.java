package ru.jabka.filmplus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabka.filmplus.exception.DataBaseException;
import ru.jabka.filmplus.exception.NoDataFoundException;
import ru.jabka.filmplus.model.Review;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {
    private static String INSERT = """
            INSERT 
              INTO filmplus.review (user_id, movie_id, review_text, review_date) 
            VALUES (:user_id, :movie_id, :review_text, :review_date)
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;


    public void insert(Review review) {
        try {
            jdbcTemplate.update(INSERT, reviewToSql(review));
        } catch (DataIntegrityViolationException e) {
            handleDbExceptionMessage(e.getMessage(), review);
        } catch (Exception e) {
            throw DataBaseException.create(e.getMessage());
        }
    }

    private MapSqlParameterSource reviewToSql(Review review) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("user_id", review.userId());
        params.addValue("movie_id", review.movieId());
        params.addValue("review_text", review.reviewText());
        params.addValue("review_date", review.reviewDate());

        return params;
    }

    private void handleDbExceptionMessage(String message, Review review) {
        if (message.contains("review_user_fk")) {
            throw NoDataFoundException.create(String.format("Пользователь не найден [id = %s]", review.userId()));
        } else if (message.contains("review_movie_fk")) {
            throw NoDataFoundException.create(String.format("Фильм не найден [id = %s]", review.movieId()));
        }
    }
}
