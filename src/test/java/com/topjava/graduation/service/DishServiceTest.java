package com.topjava.graduation.service;

import com.topjava.graduation.data.RestaurantTestData;
import com.topjava.graduation.model.Dish;
import com.topjava.graduation.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.topjava.graduation.TestUtil.*;
import static com.topjava.graduation.data.DishTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService dishService;

    @Test
    void get() {
        DISH_MATCHER.assertMatch(dishService.get(DISH_ID), dish);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> dishService.get(NOT_FOUND));
    }

    @Test
    void getAll() {
        DISH_MATCHER.assertMatch(dishService.getAll(), allDishes());
    }

    @Test
    void delete() {
        dishService.delete(DISH_ID);
        assertThrows(NotFoundException.class, () -> dishService.get(DISH_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> dishService.delete(NOT_FOUND));
    }

    @Test
    void create() {
        Dish newDish = getNew();
        newDish.setRestaurant(RestaurantTestData.restaurant);
        Dish created = dishService.create(newDish);
        newDish.setId(created.id());
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(dishService.get(created.id()), newDish);
    }

    @Test
    void createNull() {
        assertThrows(IllegalArgumentException.class, () -> dishService.create(null));
    }

    @Test
    void update() {
        Dish updated = getUpdated();
        updated.setRestaurant(RestaurantTestData.restaurant);
        dishService.update(updated);
        DISH_MATCHER.assertMatch(dishService.get(DISH_ID), updated);
    }

    @Test
    void updateNull() {
        assertThrows(IllegalArgumentException.class, () -> dishService.update(null));
    }

    @Test
    void updateNotFound() {
        Dish updated = getUpdated();
        updated.setId(NOT_FOUND);
        assertThrows(NotFoundException.class, () -> dishService.update(updated));
    }
}
