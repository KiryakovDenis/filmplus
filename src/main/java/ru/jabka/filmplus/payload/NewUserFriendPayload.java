package ru.jabka.filmplus.payload;

public class NewUserFriendPayload {
    private final Long userId;
    private final Long FriendId;

    public Long getUserId() {
        return userId;
    }

    public Long getFriendId() {
        return FriendId;
    }

    public NewUserFriendPayload(Long userId, Long friendId) {
        this.userId = userId;
        FriendId = friendId;
    }
}