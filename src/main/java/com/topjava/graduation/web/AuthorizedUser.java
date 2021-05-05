package com.topjava.graduation.web;

import com.topjava.graduation.model.User;

import java.io.Serial;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User{
    @Serial
    private static final long serialVersionUID = 1L;

    private User user;

    public AuthorizedUser(User u) {
        super(u.getEmail(), u.getPassword(), u.isEnabled(), true, true, true, u.getRoles());
        this.user = u;
    }

    public int getId() {
        return user.id();
    }

    public void setUser(User u) {
        u.setPassword(null);
        user = u;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return user.toString();
    }
}
