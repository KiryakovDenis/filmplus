package ru.jabka.filmplus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.exception.DataBaseException;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.repository.UserRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private final User validUser = User.builder()
            .id(1L)
            .name("Иванов И И")
            .login("IVANOV_I")
            .email("ivanov_i@mail.ru")
            .birthday(LocalDate.of(1977, 3, 8))
            .build();

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

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Создание валидного пользователя")
    void createValidUser() {
        Mockito.when(userRepository.insert(validUser)).thenReturn(validUser);

        User result = userService.create(validUser);

        assertThat(result).isEqualTo(validUser);

        verify(userRepository).insert(validUser);
    }

    @Test
    @DisplayName("Ошибка БД при создании валидного пользователя")
    void createValidUser_checkDataBaseException() {
        Mockito.when(userRepository.insert(validUser)).thenThrow(DataBaseException.class);

        DataBaseException dbException = assertThrows(DataBaseException.class, () -> {
            userService.create(validUser);
        });

        verify(userRepository).insert(validUser);
    }

    @Test
    @DisplayName("Успешное получение пользователя по id")
    void getById_success() {
        Mockito.when(userRepository.getById(1L)).thenReturn(validUser);
        User result = userService.getById(1L);

        assertThat(result).isEqualTo(validUser);

        verify(userRepository).getById(1L);
    }

    @Test
    @DisplayName("Успешное обновление валидного пользователя")
    void update_succsess() {
        Mockito.when(userRepository.update(validUser)).thenReturn(validUser);
        User result = userService.update(validUser);

        assertThat(result).isEqualTo(validUser);

        verify(userRepository).update(validUser);
    }

    @Test
    @DisplayName("Валидация пустого имени пользователя")
    void createEmptyNameUser_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            userService.create(emptyNameUser);
        });
    }

    @Test
    @DisplayName("Валидация пустого email")
    void createEmptyEmailUser_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            userService.create(emptyEmailUser);
        });
    }

    @Test
    @DisplayName("Валидация неуказанной даты рождения")
    void createEmptyBirthdayUser_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            userService.create(emptyBirthdayUser);
        });
    }

    @Test
    @DisplayName("Валидация пустого логина")
    void createEmptyLoginUser_checkBadRequestException() {
        BadRequestException brException = assertThrows(BadRequestException.class, () -> {
            userService.create(emptyLoginUser);
        });
    }
}