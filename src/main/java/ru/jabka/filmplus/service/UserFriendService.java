package ru.jabka.filmplus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jabka.filmplus.model.UserFriend;
import ru.jabka.filmplus.repository.UserFriendRepository;
import ru.jabka.filmplus.validator.UserFriendValidator;

@Service
@RequiredArgsConstructor
public class UserFriendService {
    private final UserFriendRepository userFriendRepository;

    public void create(final UserFriend userFriend) {
        UserFriendValidator.validate(userFriend);
        userFriendRepository.insert(userFriend);
    }
}