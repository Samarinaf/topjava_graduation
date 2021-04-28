package com.topjava.graduation.service;

import com.topjava.graduation.model.Restaurant;
import com.topjava.graduation.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.topjava.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(restaurantRepository.getOne(id), id);
    }

    public void delete(int id) {
        checkNotFoundWithId(restaurantRepository.delete(id) != 0, id);
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurantRepository.getOne(restaurant.id()), restaurant.id());
    }
}
