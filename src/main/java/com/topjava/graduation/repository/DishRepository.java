package com.topjava.graduation.repository;

import com.topjava.graduation.model.Dish;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id=:id")
    int delete(@Param("id") int id);

    Dish findById(int id);

    @EntityGraph(attributePaths = {"restaurant"})
    @Query("SELECT d FROM Dish d ORDER BY d.name, d.price")
    List<Dish> findAll();

    @EntityGraph(attributePaths = {"restaurant"})
    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restId AND d.date=:date ORDER BY d.name, d.price")
    List<Dish> findAllByRestaurantAndDate(@Param("restId") int restaurantId, @Param("date") LocalDate date);
}
