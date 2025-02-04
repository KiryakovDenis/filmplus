package ru.jabka.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.payload.NewUserPayload;
import ru.jabka.filmplus.payload.UpdateUserPayload;
import ru.jabka.filmplus.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "Пользователи")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Создать пользователя")
    public User create(@RequestBody final NewUserPayload user) {
        return userService.create(user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по id")
    public User get(@PathVariable final Long id) {
        return userService.getById(id);
    }

    @PatchMapping
    @Operation(summary = "Обновление пользователя")
    public User update(@RequestBody final UpdateUserPayload user) {
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление пользователя")
    public void delete(@PathVariable final Long id) {
        userService.delete(id);
    }

    @PostMapping("/{id}/friend")
    @Operation(summary = "Добавить пользователю друга")
    public User addFriend(@PathVariable(name="id") Long id,
                          @RequestParam(required = true) Long friendId) {
        return this.userService.addFriend(id, friendId);
    }
}