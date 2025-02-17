package ru.jabka.filmplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public User create(final User user) {
        validate(user);
        return userRepository.insert(user);
    }

    @Transactional(readOnly = true)
    public User getById(final Long id) {
        return userRepository.getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public User update(final User user) {
        validate(user);
        return userRepository.update(user);
    }

    private static void validate(User user) {
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