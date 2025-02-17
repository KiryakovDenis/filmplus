package ru.jabka.filmplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.UserFriend;
import ru.jabka.filmplus.repository.UserFriendRepository;

@Service
@RequiredArgsConstructor
public class UserFriendService {
    private final UserFriendRepository userFriendRepository;

    @Transactional(rollbackFor = Exception.class)
    public void create(final UserFriend userFriend) {
        validate(userFriend);
        userFriendRepository.insert(userFriend);
    }

    private void validate(UserFriend userFriend) {
        if (userFriend.getUserId().equals(userFriend.getFriendId())) {
            throw BadRequestException.create("Пользователь не может добавить себе в друзья самого себя.");
        }
    }
}