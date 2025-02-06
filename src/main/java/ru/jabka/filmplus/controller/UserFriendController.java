package ru.jabka.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.model.User;
import ru.jabka.filmplus.payload.NewUserFriendPayload;
import ru.jabka.filmplus.service.UserService;

@RestController
@RequestMapping("/api/v1/friends")
@Tag(name = "Друзья пользователей")
public class UserFriendController {

    private final UserService userService;

    public UserFriendController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{id}/friend")
    @Operation(summary = "Добавить пользователю друга")
    public User addFriend(@RequestBody NewUserFriendPayload userFriendPayload) {
        if (userFriendPayload.getUserId().equals(userFriendPayload.getFriendId())) {
            throw new BadRequestException("Пользователь не может добавить в качестве друга самого себя");
        }
        return this.userService.addFriend(userFriendPayload);
    }



}
