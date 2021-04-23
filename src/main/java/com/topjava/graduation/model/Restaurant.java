package com.topjava.graduation.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractBaseEntity {

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<Dish> lunchMenu;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name, List<Dish> lunchMenu) {
        super(id);
        this.name = name;
        this.lunchMenu = lunchMenu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dish> getLunchMenu() {
        return lunchMenu;
    }

    public void setLunchMenu(List<Dish> lunchMenu) {
        this.lunchMenu = lunchMenu;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
