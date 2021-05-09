package com.topjava.graduation.web.controller.dish;

import com.topjava.graduation.model.Dish;
import com.topjava.graduation.service.DishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = DishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {
    static final String REST_URL = "/lunch-menu";
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DishService dishService;

    @GetMapping("/{restaurantId}")
    public List<Dish> getLunchMenuByDate(@PathVariable int restaurantId,
                                         @RequestParam(value = "date", required = false)
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDate required = date == null ? LocalDate.now() : date;
        log.info("get all dishes by restaurant id={} and date={}", restaurantId, required);
        return dishService.getAllByRestaurantAndDate(restaurantId, required);
    }
}
