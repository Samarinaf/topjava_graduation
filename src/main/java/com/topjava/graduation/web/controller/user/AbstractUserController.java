package com.topjava.graduation.web.controller.user;

import com.topjava.graduation.model.User;
import com.topjava.graduation.service.UserService;
import com.topjava.graduation.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    public List<User> getAll() {
        log.info("get all users");
        return userService.getAll();
    }

    public User get(int id) {
        log.info("get user by id={}", id);
        return userService.get(id);
    }

    public User create(User user) {
        log.info("create {}", user);
        ValidationUtil.checkNew(user);
        return userService.create(user);
    }

    public void delete(int id) {
        log.info("delete user with id={}", id);
        userService.delete(id);
    }

    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        ValidationUtil.assureIdConsistent(user, id);
        userService.update(user);
    }

    public User getByEmail(String email) {
        log.info("get user by email={}", email);
        return userService.getByEmail(email);
    }
}
