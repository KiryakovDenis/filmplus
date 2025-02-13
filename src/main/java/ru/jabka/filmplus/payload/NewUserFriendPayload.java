package ru.jabka.filmplus.payload;

public class NewUserFriendPayload {
    private final Long userId;
    private final Long friendId;

    public Long getUserId() {
        return userId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public NewUserFriendPayload(Long userId, Long friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }
}