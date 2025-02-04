package ru.jabka.filmplus.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.jabka.filmplus.exception.BadRequestException;

import java.time.LocalDate;
import java.util.*;

public class User {

    private Long id;
    private String name;
    private String email;
    private String login;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private Map<Long, String> friends = new HashMap<>();

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public User(Long id, String name, String email, String login, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.birthday = birthday;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User addFriend(User friend) {
        if (this.id.equals(friend.getId())) {
            throw new BadRequestException("Пользователь не может добавить в качестве друга самого себя");
        }

        this.friends.put(friend.getId(), friend.getName());
        return this;
    }

    public Map<Long, String> getFriends() {
        return this.friends;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", birthday=" + birthday +
                ", friends=" + friends +
                '}';
    }
}