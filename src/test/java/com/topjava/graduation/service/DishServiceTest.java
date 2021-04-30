package com.topjava.graduation.service;

import com.topjava.graduation.TestMatcher;
import com.topjava.graduation.model.Dish;
import com.topjava.graduation.model.Restaurant;
import com.topjava.graduation.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.topjava.graduation.data.DishTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService dishService;

    @Test
    public void get() {
        DISH_MATCHER.assertMatch(dishService.get(DISH_ID), dish);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> dishService.get(TestMatcher.NOT_FOUND)); //id = 1
    }

    @Test
    public void getAll() {
        DISH_MATCHER.assertMatch(dishService.getAll(), allDishes());
    }

    @Test
    public void delete() {
        dishService.delete(DISH_ID);
        assertThrows(NotFoundException.class, () -> dishService.get(DISH_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> dishService.delete(TestMatcher.NOT_FOUND));
    }

    @Test
    public void create() {
        Dish newDish = getNew();
        newDish.setRestaurant(new Restaurant(1002, "BlueRestaurant"));
        Dish created = dishService.create(newDish);
        newDish.setId(created.id());
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(dishService.get(created.id()), newDish);
    }

    @Test
    public void createNull() {
        assertThrows(IllegalArgumentException.class, () -> dishService.create(null));
    }

    @Test
    public void update() {
        Dish updated = getUpdated();
        updated.setRestaurant(new Restaurant(1002, "BlueRestaurant"));
        dishService.update(updated);
        DISH_MATCHER.assertMatch(dishService.get(DISH_ID), updated);
    }

    @Test
    public void updateNull() {
        assertThrows(IllegalArgumentException.class, () -> dishService.update(null));
    }

    @Test
    public void updateNotFound() {
        Dish updated = getUpdated();
        updated.setId(TestMatcher.NOT_FOUND);
        assertThrows(NotFoundException.class, () -> dishService.update(updated));
    }
}
