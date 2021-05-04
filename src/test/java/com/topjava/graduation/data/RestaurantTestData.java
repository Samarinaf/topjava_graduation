package com.topjava.graduation.data;

import com.topjava.graduation.TestMatcher;
import com.topjava.graduation.model.AbstractBaseEntity;
import com.topjava.graduation.model.Restaurant;

import java.util.Arrays;
import java.util.List;

public class RestaurantTestData {
    public static final TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingIgnoringFieldsComparator("lunchMenu");
    public static final TestMatcher<Restaurant> RESTAURANT_WITH_LUNCH_MENU_MATCHER = TestMatcher.usingIgnoringFieldsComparator();

    public static final int RESTAURANT_ID = AbstractBaseEntity.START_SEQ + 2;

    public static final Restaurant restaurant = new Restaurant(RESTAURANT_ID, "BlueRestaurant");
    public static final Restaurant restaurant_1 = new Restaurant(RESTAURANT_ID + 1, "RedRestaurant");
    public static final Restaurant restaurant_2 = new Restaurant(RESTAURANT_ID + 2, "BlackRestaurant");
    public static final Restaurant restaurant_3 = new Restaurant(RESTAURANT_ID + 3, "YellowRestaurant");

    public static Restaurant getNew() {
        return new Restaurant(null, "NewRestaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_ID, "UpdatedRestaurant");
    }

    public static List<Restaurant> allRestaurants() {
        return Arrays.asList(restaurant, restaurant_1, restaurant_2, restaurant_3);
    }
}
