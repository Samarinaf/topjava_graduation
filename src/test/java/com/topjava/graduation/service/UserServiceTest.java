package com.topjava.graduation.service;

import com.topjava.graduation.model.User;
import com.topjava.graduation.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.util.Arrays;

import static com.topjava.graduation.TestUtil.*;
import static com.topjava.graduation.data.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void get() {
        USER_MATCHER.assertMatch(userService.get(USER_ID), user);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> userService.get(NOT_FOUND));
    }

    @Test
    void getByEmail() {
        USER_MATCHER.assertMatch(userService.getByEmail("admin@gmail.com"), admin);
    }

    @Test
    void getAll() {
        USER_MATCHER.assertMatch(userService.getAll(), Arrays.asList(user, admin));
    }

    @Test
    void delete() {
        userService.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> userService.get(USER_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> userService.delete(NOT_FOUND));
    }

    @Test
    void create() {
        User created = userService.create(getNew());
        User newUser = getNew();
        newUser.setId(created.id());
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(userService.get(created.id()), newUser);
    }

    @Test
    void createWithDuplicateEmail() {
        assertThrows(DataAccessException.class, () -> userService.create(getNewWithDuplicateEmail()));
    }

    @Test
    void createNull() {
        assertThrows(IllegalArgumentException.class, () -> userService.create(null));
    }

    @Test
    void update() {
        userService.update(getUpdated());
        USER_MATCHER.assertMatch(userService.get(USER_ID), getUpdated());
    }

    @Test
    void updateNull() {
        assertThrows(IllegalArgumentException.class, () -> userService.update(null));
    }

    @Test
    void updateNotFound() {
        User updated = getUpdated();
        updated.setId(NOT_FOUND);
        assertThrows(NotFoundException.class, () -> userService.update(updated));
    }
}
