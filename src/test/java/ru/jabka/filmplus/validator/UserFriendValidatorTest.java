package ru.jabka.filmplus.validator;

import org.junit.jupiter.api.Test;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.UserFriend;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserFriendValidatorTest {

    private final UserFriend userEqualsFriend = new UserFriend(1L, 1L);
    @Test
    void validateUsertEqualsFriend_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            UserFriendValidator.validate(userEqualsFriend);
        });
    }
}
