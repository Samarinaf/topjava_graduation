package com.topjava.graduation.model;

public class Dish extends AbstractBaseEntity {
    private String name;
    private Integer price;

    public Dish() {
    }

    public Dish(Integer id, String name, Integer price) {
        super(id);
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
