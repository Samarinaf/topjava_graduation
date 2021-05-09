package com.topjava.graduation.web.controller.dish;

import com.topjava.graduation.model.Dish;
import com.topjava.graduation.service.DishService;
import com.topjava.graduation.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = DishAdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishAdminRestController {
    static final String REST_URL = "/admin/dishes";
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DishService dishService;

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id) {
        log.info("get dish by id={}", id);
        return dishService.get(id);
    }

    @GetMapping
    public List<Dish> getAll() {
        log.info("get all dishes");
        return dishService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete dish by id={}", id);
        dishService.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@Valid @RequestBody Dish dish) {
        log.info("create {}", dish);
        ValidationUtil.checkNew(dish);
        Dish created = dishService.create(dish);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int id) {
        log.info("update {} by id={}", dish, id);
        ValidationUtil.assureIdConsistent(dish, id);
        dishService.update(dish);
    }
}
