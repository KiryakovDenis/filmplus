package ru.jabka.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.payload.NewUserFriendPayload;
import ru.jabka.filmplus.service.UserService;

@RestController
@RequestMapping("/api/v1/friend")
@Tag(name = "Друзья пользователей")
public class UserFriendController {

    private final UserService userService;

    public UserFriendController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    @Operation(summary = "Добавить пользователю друга")
    public User addFriend(@RequestBody NewUserFriendPayload userFriendPayload) {
        return this.userService.addFriend(userFriendPayload);
    }
}
