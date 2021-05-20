package com.topjava.graduation.service;

import com.topjava.graduation.model.Dish;
import com.topjava.graduation.repository.DishRepository;
import com.topjava.graduation.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.topjava.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    public DishService(DishRepository dishRepository, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Dish get(int id) {
        return checkNotFoundWithId(dishRepository.findById(id), id);
    }

    public void delete(int id) {
        checkNotFoundWithId(dishRepository.delete(id) != 0, id);
    }

    public List<Dish> getAll() {
        return dishRepository.findAll();
    }

    @Transactional
    public List<Dish> getAllByRestaurantAndDate(int restaurantId, LocalDate date) {
        checkNotFoundWithId(restaurantRepository.findById(restaurantId), restaurantId);
        return dishRepository.findAllByRestaurantAndDate(restaurantId, date);
    }

    public Dish create(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        return dishRepository.save(dish);
    }

    @Transactional
    public void update(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(dishRepository.findById(dish.id()), dish.id());
        dishRepository.save(dish);
    }
}
