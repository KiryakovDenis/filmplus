package ru.jabka.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabka.filmplus.model.UserFriend;
import ru.jabka.filmplus.service.UserFriendService;

@RestController
@RequestMapping("/api/v1/friends")
@RequiredArgsConstructor
@Tag(name = "Друзья пользователей")
public class UserFriendController {

    private final UserFriendService userFriendService;

    @PostMapping
    @Operation(summary = "Добавить пользователю друга")
    public void addFriend(@RequestBody UserFriend userFriend) {
        userFriendService.create(userFriend);
    }
}