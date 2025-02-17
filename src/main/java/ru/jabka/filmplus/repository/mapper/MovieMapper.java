package ru.jabka.filmplus.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.jabka.filmplus.model.Genre;
import ru.jabka.filmplus.model.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class MovieMapper implements RowMapper<Movie> {

    @Override
    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Movie.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .description(rs.getString("description"))
                .releaseDate(rs.getObject("release_date", LocalDate.class))
                .genre(Genre.valueOf(rs.getString("genre")))
                .duration(rs.getLong("duration"))
                .build();
    }
}
