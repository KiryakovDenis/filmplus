package ru.jabka.filmplus.validator;

import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.UserFriend;

public class UserFriendValidator {
    public static void validate(UserFriend userFriend) {
        if (userFriend.userId().equals(userFriend.friendId())) {
            throw BadRequestException.create("Пользователь не может добавить себе в друзья самого себя.");
        }
    }
}
