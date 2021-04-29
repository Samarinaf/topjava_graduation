package com.topjava.graduation.service;

import com.topjava.graduation.model.Role;
import com.topjava.graduation.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import static com.topjava.graduation.data.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void create() {
        User created = userService.create(getNew());
        User newUser = getNew();
        newUser.setId(created.id());

        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(userService.get(created.id()), newUser);
    }

    @Test
    public void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () -> userService.create(
                new User(null, "Duplicate", "user@yandex.ru", "duplicatePass", Role.USER)));
    }

    @Test
    @Transactional
    public void get() {
        USER_MATCHER.assertMatch(userService.get(USER_ID), user); //fail --> java.lang.AssertionError (PROXY)
    }

}
