package ru.jabka.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.service.UserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "Пользователи")

public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Создать пользователя")
    public User create(@RequestBody final User user) {
        return userService.create(user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по id")
    public User get(@PathVariable final Long id) {
        return userService.getById(id);
    }

    @PatchMapping
    @Operation(summary = "Обновление пользователя")
    public User update(@RequestBody final User user) {
        return userService.update(user);
    }
}