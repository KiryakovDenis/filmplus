package ru.jabka.filmplus.repository;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabka.filmplus.exception.DataBaseException;
import ru.jabka.filmplus.exception.NoDataFoundException;
import ru.jabka.filmplus.model.UserFriend;

@Repository
@AllArgsConstructor
public class UserFriendRepository {

    private static String INSERT = """
            INSERT INTO filmplus.user_friend (user_id, friend_id) VALUES(:user_id, :friend_id);
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public void insert(UserFriend userFriend) {
        try {
            jdbcTemplate.update(INSERT, userFriendToSql(userFriend));
        } catch (DataIntegrityViolationException e) {
            handleDbExceptionMessage(e.getMessage(), userFriend);
        } catch (Exception e) {
            throw DataBaseException.create(e.getMessage());
        }
    }

    private MapSqlParameterSource userFriendToSql(final UserFriend userFriend) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("user_id", userFriend.getUserId());
        params.addValue("friend_id", userFriend.getFriendId());

        return params;
    }

    private void handleDbExceptionMessage(final String message, final UserFriend userFriend) {
        if (message.contains("user_friend_user_friend_fk")) {
            throw NoDataFoundException.create(String.format("Пользователь не найден [id = %s]", userFriend.getFriendId()));
        } else if (message.contains("user_friend_user_user_fk")) {
            throw NoDataFoundException.create(String.format("Пользователь не найден [id = %s]", userFriend.getUserId()));
        } else if (message.contains("user_friend_ch1")) {
            throw NoDataFoundException.create("Пользователь не может добавить в друзья сам себя");
        } else {
            throw DataBaseException.create(message);
        }
    }
}