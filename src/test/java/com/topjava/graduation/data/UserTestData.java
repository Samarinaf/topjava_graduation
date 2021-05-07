package com.topjava.graduation.data;

import com.topjava.graduation.TestMatcher;
import com.topjava.graduation.model.AbstractBaseEntity;
import com.topjava.graduation.model.Role;
import com.topjava.graduation.model.User;

import java.util.Collections;

public class UserTestData {
    public static final TestMatcher<User> USER_MATCHER = TestMatcher.usingIgnoringFieldsComparator(User.class, "password");

    public static final int USER_ID = AbstractBaseEntity.START_SEQ;
    public static final int ADMIN_ID = AbstractBaseEntity.START_SEQ + 1;

    public static final User user = new User(USER_ID, "User", "user@yandex.ru", "userPassword", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", "admin@gmail.com", "adminPassword", Role.ADMIN);

    public static User getNew() {
        return new User(null, "NewUser", "newuser@gmail.com", "newPassword", false, Collections.singleton(Role.USER));
    }

    public static User getNewWithDuplicateEmail() {
        return new User(null, "Duplicate", "user@yandex.ru", "duplicatePassword", Role.USER);
    }

    public static User getUpdated() {
        User updated = new User(user);
        updated.setEmail("update@gmail.com");
        updated.setName("UpdatedName");
        updated.setPassword("newPass");
        updated.setEnabled(false);
        updated.setRoles(Collections.singleton(Role.ADMIN));
        return updated;
    }
}
