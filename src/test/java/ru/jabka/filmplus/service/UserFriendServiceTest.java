package ru.jabka.filmplus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.UserFriend;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserFriendServiceTest {

    private final UserFriend userEqualsFriend = new UserFriend(1L, 1L);

    @InjectMocks
    private UserFriendService userFriendService;

    @Test
    @DisplayName("Валидация на совпадение userId и friendId")
    void validateUsertEqualsFriend_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            userFriendService.create(userEqualsFriend);
        });
    }
}