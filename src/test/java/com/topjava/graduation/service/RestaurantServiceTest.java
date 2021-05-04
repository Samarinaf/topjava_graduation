package com.topjava.graduation.service;

import com.topjava.graduation.TestMatcher;
import com.topjava.graduation.data.DishTestData;
import com.topjava.graduation.model.Restaurant;
import com.topjava.graduation.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static com.topjava.graduation.data.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Test
    public void get() {
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(RESTAURANT_ID), restaurant);
    }

    @Test
    public void detWithDishes() {
        Restaurant created = restaurant;
        created.setLunchMenu(Arrays.asList(DishTestData.dish, DishTestData.dish_3, DishTestData.dish_8));
        RESTAURANT_WITH_LUNCH_MENU_MATCHER.assertMatch(restaurantService.getWithDishes(RESTAURANT_ID), created);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> restaurantService.get(TestMatcher.NOT_FOUND)); //id = 1
    }

    @Test
    public void getAll() {
        RESTAURANT_MATCHER.assertMatch(restaurantService.getAll(), allRestaurants());
    }

    @Test
    public void delete() {
        restaurantService.delete(RESTAURANT_ID);
        assertThrows(NotFoundException.class, () -> restaurantService.get(RESTAURANT_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> restaurantService.delete(TestMatcher.NOT_FOUND));
    }

    @Test
    public void create() {
        Restaurant newRest = getNew();
        Restaurant created = restaurantService.create(newRest);
        newRest.setId(created.id());
        RESTAURANT_MATCHER.assertMatch(created, newRest);
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(created.id()), newRest);
    }

    @Test
    public void createNull() {
        assertThrows(IllegalArgumentException.class, () -> restaurantService.create(null));
    }

    @Test
    public void update() {
        restaurantService.update(getUpdated());
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(RESTAURANT_ID), getUpdated());
    }

    @Test
    public void updateNull() {
        assertThrows(IllegalArgumentException.class, () -> restaurantService.update(null));
    }

    @Test
    public void updateNotFound() {
        Restaurant updated = getUpdated();
        updated.setId(TestMatcher.NOT_FOUND);
        assertThrows(NotFoundException.class, () -> restaurantService.update(updated));
    }
}
