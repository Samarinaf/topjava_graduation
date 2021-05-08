package com.topjava.graduation.service;

import com.topjava.graduation.model.Restaurant;
import com.topjava.graduation.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.topjava.graduation.TestUtil.*;
import static com.topjava.graduation.data.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void get() {
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(RESTAURANT_ID), restaurant);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> restaurantService.get(NOT_FOUND));
    }

    @Test
    void getAll() {
        RESTAURANT_MATCHER.assertMatch(restaurantService.getAll(), allRestaurants());
    }

    @Test
    void delete() {
        restaurantService.delete(RESTAURANT_ID);
        assertThrows(NotFoundException.class, () -> restaurantService.get(RESTAURANT_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> restaurantService.delete(NOT_FOUND));
    }

    @Test
    void create() {
        Restaurant newRest = getNew();
        Restaurant created = restaurantService.create(newRest);
        newRest.setId(created.id());
        RESTAURANT_MATCHER.assertMatch(created, newRest);
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(created.id()), newRest);
    }

    @Test
    void createNull() {
        assertThrows(IllegalArgumentException.class, () -> restaurantService.create(null));
    }

    @Test
    void update() {
        restaurantService.update(getUpdated());
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(RESTAURANT_ID), getUpdated());
    }

    @Test
    void updateNull() {
        assertThrows(IllegalArgumentException.class, () -> restaurantService.update(null));
    }

    @Test
    void updateNotFound() {
        Restaurant updated = getUpdated();
        updated.setId(NOT_FOUND);
        assertThrows(NotFoundException.class, () -> restaurantService.update(updated));
    }
}
