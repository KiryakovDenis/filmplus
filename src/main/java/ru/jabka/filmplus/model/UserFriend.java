package ru.jabka.filmplus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserFriend {
    private Long userId;
    private Long friendId;
}