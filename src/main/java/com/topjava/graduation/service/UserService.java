package com.topjava.graduation.service;

import com.topjava.graduation.model.User;
import com.topjava.graduation.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.topjava.graduation.util.ValidationUtil.checkNotFound;
import static com.topjava.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User get(int id) {
        return checkNotFoundWithId(userRepository.findById(id).orElse(null), id);
    }

    public void delete(int id) {
        checkNotFoundWithId(userRepository.delete(id) != 0, id);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return userRepository.save(user);
    }

    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(userRepository.findById(user.id()).orElse(null), user.id());
        userRepository.save(user);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(userRepository.getByEmail(email), "email=" + email);
    }
}
