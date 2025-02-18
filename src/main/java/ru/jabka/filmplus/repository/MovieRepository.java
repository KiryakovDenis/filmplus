package ru.jabka.filmplus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.exception.DataBaseException;
import ru.jabka.filmplus.exception.NoDataFoundException;
import ru.jabka.filmplus.model.Movie;
import ru.jabka.filmplus.payload.SearchMoviePayload;
import ru.jabka.filmplus.repository.mapper.MovieMapper;

import java.sql.Types;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MovieRepository {
    private final String SELECT_BY_ID = """
            SELECT *
              FROM filmplus.movie a
             WHERE a.id = :id;
            """;

    private final String INSERT = """
            INSERT INTO filmplus.movie (title, description, release_date, duration, genre) 
            VALUES (:title, :description, :release_date, :duration, :genre) RETURNING *
            """;

    private final String UPDATE = """
               UPDATE filmplus.movie
               SET title = :title,
                   description = :description,
                   release_date = :release_date,
                   duration = :duration,
                   genre = :genre
             WHERE id = :id
             RETURNING *
            """;

    private final String SELECT_FOR_SEARCH = """
            SELECT *
              FROM filmplus.movie  a
             WHERE (:title::TEXT IS NULL OR a.title LIKE :title::TEXT)
                   AND
                   (:duration::INT IS NULL OR a.duration = :duration::INT)
                   AND
                   (:genre::TEXT IS NULL OR a.genre = :genre::TEXT)
                   AND a.release_date BETWEEN coalesce(:begin_date::DATE, a.release_date)
                                          AND coalesce(:end_date::DATE, a.release_date)
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final MovieMapper movieMapper;


    public Movie insert(final Movie movie) {
        try {
            return jdbcTemplate.queryForObject(INSERT, movieToSql(movie), movieMapper);
        } catch (Exception e) {
            throw DataBaseException.create(e.getMessage());
        }
    }

    public Movie update(final Movie movie) {
        try {
            return jdbcTemplate.queryForObject(UPDATE, movieToSql(movie), movieMapper);
        } catch (Exception e) {
            throw DataBaseException.create(e.getMessage());
        }
    }

    public List<Movie> search(final SearchMoviePayload search) {
        try {
            return jdbcTemplate.query(SELECT_FOR_SEARCH, searchMovieToSql(search), movieMapper);
        } catch (EmptyResultDataAccessException e) {
            throw NoDataFoundException.create(String.format("Фильмы по заданным условиям - не найдены [%s]", search.toString()));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public Movie getById(final Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID, new MapSqlParameterSource("id", id), movieMapper);
        } catch (EmptyResultDataAccessException e) {
            throw NoDataFoundException.create(String.format(String.format("Фильм не найден [id = %s]", id)));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    private MapSqlParameterSource movieToSql(final Movie movie) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", movie.getId());
        params.addValue("title", movie.getTitle());
        params.addValue("description", movie.getDescription());
        params.addValue("release_date", movie.getReleaseDate());
        params.addValue("duration", movie.getDuration());
        params.addValue("genre", movie.getGenre().name());


        return params;
    }

    private MapSqlParameterSource searchMovieToSql(final SearchMoviePayload search) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        if (search.getBeginDate() == null) {
            params.addValue("begin_date", null, Types.NULL);
        } else {
            params.addValue("begin_date", search.getBeginDate());
        }

        if (search.getEndDate() == null) {
            params.addValue("end_date", null, Types.NULL);
        } else {
            params.addValue("end_date", search.getEndDate());
        }

        if (search.getTitle() == null || search.getTitle().isBlank()) {
            params.addValue("title", null, Types.NULL);
        } else {
            params.addValue("title", search.getTitle());
        }

        if (search.getDuration() == null) {
            params.addValue("duration", null, Types.NULL);
        } else {
            params.addValue("duration", search.getDuration());
        }

        if (search.getGenre() == null) {
            params.addValue("genre", null, Types.NULL);
        } else {
            params.addValue("genre", search.getGenre().name());
        }

        return params;
    }
}