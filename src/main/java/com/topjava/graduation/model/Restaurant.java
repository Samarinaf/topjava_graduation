package com.topjava.graduation.model;

public class Restaurant extends AbstractBaseEntity {
    private String name;
    private Double rating;
    private Menu menu;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name, Double rating, Menu menu) {
        super(id);
        this.name = name;
        this.rating = rating;
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
