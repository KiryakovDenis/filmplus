package ru.jabka.filmplus.validator;

import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.User;

public class UserValidator {
    public static void validate(User user) {
        if (user == null) {
            throw BadRequestException.create("Введите информацию о пользователе");
        }
        if (!StringUtils.hasText(user.getName())) {
            throw BadRequestException.create("Укажите имя пользователя!");
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw BadRequestException.create("Необходимо указать адрес электронной почты пользователя!");
        }

        if (user.getBirthday() == null) {
            throw BadRequestException.create("Необходимо указать день рождения пользователя!");
        }

        if (!StringUtils.hasText(user.getLogin())) {
            throw BadRequestException.create("Необходимо указать логин пользователя!");
        }
    }
}
