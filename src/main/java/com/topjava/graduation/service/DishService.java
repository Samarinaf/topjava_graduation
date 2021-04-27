package com.topjava.graduation.service;

import com.topjava.graduation.model.Dish;
import com.topjava.graduation.repository.DishRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.topjava.graduation.util.ValidationUtil.*;

@Service
public class DishService {

    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public Dish get(int id) {
        return checkNotFoundWithId(dishRepository.getOne(id), id);
    }

    public void delete(int id) {
        checkNotFoundWithId(dishRepository.delete(id) != 0, id);
    }

    public List<Dish> getAllByDate(LocalDate date, int restId) {
        return dishRepository.getAllByDate(date, restId);
    }

    public Dish create(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        return dishRepository.save(dish);
    }

    public void update(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(dishRepository.getOne(dish.getId()), dish.id());
        dishRepository.save(dish);
    }
}
