package com.topjava.graduation.service;

import com.topjava.graduation.model.User;
import com.topjava.graduation.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.util.Arrays;

import static com.topjava.graduation.data.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void create() {
        User created = userService.create(getNew());
        User newUser = getNew();
        newUser.setId(created.id());
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(userService.get(created.id()), newUser);
    }

    @Test
    public void createWithDuplicateEmail() {
        assertThrows(DataAccessException.class, () -> userService.create(getNewWithDuplicateEmail()));
    }

    @Test
    public void createNull() {
        assertThrows(IllegalArgumentException.class, () -> userService.create(null));
    }

    @Test
    public void get() {
        USER_MATCHER.assertMatch(userService.get(USER_ID), user);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> userService.get(NOT_FOUND)); //id = 1
    }

    @Test
    public void getByEmail() {
        USER_MATCHER.assertMatch(userService.getByEmail("admin@gmail.com"), admin);
    }

    @Test
    public void getAll() {
        USER_MATCHER.assertMatch(userService.getAll(), Arrays.asList(user, admin));
    }

    @Test
    public void update() {
        userService.update(getUpdated()); //user
        USER_MATCHER.assertMatch(userService.get(USER_ID), getUpdated());
    }

    @Test
    public void updateNull() {
        assertThrows(IllegalArgumentException.class, () -> userService.update(null));
    }
}
