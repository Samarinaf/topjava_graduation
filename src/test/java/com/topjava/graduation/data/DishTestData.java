package com.topjava.graduation.data;

import com.topjava.graduation.TestMatcher;
import com.topjava.graduation.model.AbstractBaseEntity;
import com.topjava.graduation.model.Dish;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.topjava.graduation.data.RestaurantTestData.*;

public class DishTestData {
    public static final TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Dish.class, "restaurant");

    public static final int DISH_ID = AbstractBaseEntity.START_SEQ + 6;

    public static final Dish dish = new Dish(DISH_ID, "Coffee", 2.95, LocalDate.of(2021, 4, 12), restaurant);
    public static final Dish dish_1 = new Dish(DISH_ID + 1, "Tea", 1.25, LocalDate.of(2021, 4, 12), restaurant_1);
    public static final Dish dish_2 = new Dish(DISH_ID + 2, "Oatmeal", 4.35, LocalDate.of(2021, 4, 11), restaurant_2);
    public static final Dish dish_3 = new Dish(DISH_ID + 3, "Omelet", 4.75, LocalDate.of(2021, 4, 12), restaurant);
    public static final Dish dish_4 = new Dish(DISH_ID + 4, "Orange Juice", 2.45, LocalDate.of(2021, 4, 11), restaurant_2);
    public static final Dish dish_5 = new Dish(DISH_ID + 5, "Salmon Pepper Rice", 7.15, LocalDate.of(2021, 4, 12), restaurant_1);
    public static final Dish dish_6 = new Dish(DISH_ID + 6, "Chicken Noodle Soup", 6.75, LocalDate.of(2021, 4, 11), restaurant_3);
    public static final Dish dish_7 = new Dish(DISH_ID + 7, "Greek Salad", 6.35, LocalDate.of(2021, 4, 11), restaurant_3);
    public static final Dish dish_8 = new Dish(DISH_ID + 8, "Cheese Tomato & Red Onion Panini", 5.95, LocalDate.of(2021, 4, 12), restaurant);
    public static final Dish dish_9 = new Dish(DISH_ID + 9, "Roast Ham & Pickle Sandwich", 5.45, LocalDate.of(2021, 4, 11), restaurant_2);

    public static Dish getNew() {
        return new Dish(null, "New Dish", 11.11, LocalDate.now(), restaurant_3);
    }

    public static Dish getUpdated() {
        return new Dish(DISH_ID, "Updated Dish", 10.10, LocalDate.now(), restaurant_2);
    }

    public static Dish getInvalid() {
        return new Dish(null, "   ", null, null, null);
    }

    public static List<Dish> allDishes() {
        return Stream.of(dish, dish_1, dish_2, dish_3, dish_4, dish_5, dish_6, dish_7, dish_8, dish_9)
                .sorted(Comparator.comparing(Dish::getName).thenComparing(Dish::getPrice))
                .collect(Collectors.toList());
    }

    public static List<Dish> getAllByBlueRestaurant() {
        return Stream.of(dish, dish_3, dish_8)
                .sorted(Comparator.comparing(Dish::getName).thenComparing(Dish::getPrice))
                .collect(Collectors.toList());
    }
}
