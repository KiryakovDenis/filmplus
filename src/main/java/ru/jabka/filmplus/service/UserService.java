package ru.jabka.filmplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.repository.UserRepository;
import ru.jabka.filmplus.validator.UserValidator;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(final User user) {
        UserValidator.validate(user);
        return userRepository.insert(user);
    }

    public User getById(final Long id) {
        return userRepository.getById(id);
    }

    public User update(final User user) {
        UserValidator.validate(user);
        return userRepository.update(user);
    }
}