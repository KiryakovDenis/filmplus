package ru.jabka.filmplus.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserValidatorTest {
    private final User emptyNameUser = User.builder()
            .id(1L)
            .login("IVANOV_I")
            .email("ivanov_i@mail.ru")
            .birthday(LocalDate.of(1977, 3, 8))
            .build();

    private final User emptyEmailUser = User.builder()
            .id(1L)
            .name("Иванов И И")
            .login("IVANOV_I")
            .birthday(LocalDate.of(1977, 3, 8))
            .build();

    private final User emptyLoginUser = User.builder()
            .id(1L)
            .name("Иванов И И")
            .email("ivanov_i@mail.ru")
            .birthday(LocalDate.of(1977, 3, 8))
            .build();

    private final User emptyBirthdayUser = User.builder()
            .id(1L)
            .name("Иванов И И")
            .login("IVANOV_I")
            .email("ivanov_i@mail.ru")
            .build();

    void createNullUser_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            UserValidator.validate(null);
        });
    }

    @Test
    void createEmptyNameUser_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            UserValidator.validate(emptyNameUser);
        });
    }

    @Test
     void createEmptyEmailUser_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            UserValidator.validate(emptyEmailUser);
        });
    }

    @Test
    void createEmptyBirthdayUser_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            UserValidator.validate(emptyBirthdayUser);
        });
    }

    @Test
    void createEmptyLoginUser_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            UserValidator.validate(emptyLoginUser);
        });
    }
}