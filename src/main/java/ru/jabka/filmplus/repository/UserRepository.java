package ru.jabka.filmplus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.exception.DataBaseException;
import ru.jabka.filmplus.exception.NoDataFoundException;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.repository.mapper.UserMapper;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final String INSERT = """
            INSERT INTO filmplus."user" ("name", email, login, birthday)  
            VALUES (:name, :email, :login, :birthday)
            RETURNING *
            """;

    private final String UPDATE = """
            UPDATE filmplus."user" 
               SET name = :name, 
                   email = :email, 
                   login = :login,
                   birthday = birthday
             WHERE id = :id
            RETURNING *
            """;

    private final String SELECT_BY_ID = """
            SELECT *
              FROM filmplus."user"
             WHERE id = :id
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

    public User insert (final User user) {
        try {
            return jdbcTemplate.queryForObject(INSERT, userToSql(user), userMapper);
        } catch (Exception e) {
            throw DataBaseException.create(e.getMessage());
        }
    }

    public User update (final User user) {
        try {
            return jdbcTemplate.queryForObject(UPDATE, userToSql(user), userMapper);
        } catch (EmptyResultDataAccessException e) {
            throw NoDataFoundException.create(String.format("Пользователь не найден [id = %s]", user.getId()));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public User getById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID, new MapSqlParameterSource().addValue("id", id), userMapper);
        } catch (EmptyResultDataAccessException e) {
            throw NoDataFoundException.create(String.format("Пользователь не найден [id = %s]", id));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    private MapSqlParameterSource userToSql(final User user) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", user.getId());
        params.addValue("name", user.getName());
        params.addValue("email", user.getEmail());
        params.addValue("login", user.getLogin());
        params.addValue("birthday", user.getBirthday());

        return params;
    }
}