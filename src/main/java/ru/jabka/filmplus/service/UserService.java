package ru.jabka.filmplus.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.payload.NewUserFriendPayload;
import ru.jabka.filmplus.payload.NewUserPayload;
import ru.jabka.filmplus.payload.UpdateUserPayload;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {

    private static final HashSet<User> users = new HashSet<>();

    public User create(final NewUserPayload user) {
        User newUser = new User((long) users.size() + 1,
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getBirthday());
        validate(newUser);
        users.add(newUser);
        return newUser;
    }

    public User getById(final Long id) {
        final User user = users.stream()
                .filter(u -> Objects.equals(u.getId(), id))
                .findFirst()
                .orElse(null);
        if (user == null) {
            throw new BadRequestException(String.format("Пользователь с id %d не найден", id));
        }
        return user;
    }

    public User update(final UpdateUserPayload user) {
        final User existUser = getById(user.getId());
        if (existUser == null) {
            return null;
        }
        existUser.setName(user.getName());
        existUser.setEmail(user.getEmail());
        validate(existUser);
        return existUser;
    }

    public void delete(final Long id) {
        User deletedUser = getById(id);
        //Проверим наличие удаляемого пользователя в друзьях других пользователей
        if (users.stream().noneMatch(u -> u.getFriends().containsKey(deletedUser.getId()))) {
            users.remove(getById(id));
        } else {
            throw new BadRequestException(String.format("Невозможно удалить пользователя %s. Он является другом другого пользователя", deletedUser.getLogin()));
        }
    }

    private void validate(final User user) {
        if (user == null) {
            throw new BadRequestException("Введите информацию о пользователе");
        }
        if (!StringUtils.hasText(user.getName())) {
            throw new BadRequestException("Укажите имя пользователя!");
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new BadRequestException("Необходимо указать адрес электронной почты пользователя!");
        }

        if (user.getBirthday() == null) {
            throw new BadRequestException("Необходимо указать день рождения пользователя!");
        }

        if (!StringUtils.hasText(user.getLogin())) {
            throw new BadRequestException("Необходимо указать логин пользователя!");
        }
    }

    public User addFriend(final NewUserFriendPayload userFriendPayload) {
        return getById(userFriendPayload.getUserId()).addFriend(getById(userFriendPayload.getFriendId()));
    }

    public Map<Long, String> getFriendsById(final Long id) {
        return getById(id).getFriends();
    }

}