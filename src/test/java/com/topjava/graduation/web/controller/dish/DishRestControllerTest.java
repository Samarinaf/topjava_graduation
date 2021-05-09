package com.topjava.graduation.web.controller.dish;

import com.topjava.graduation.data.UserTestData;
import com.topjava.graduation.model.Dish;
import com.topjava.graduation.web.controller.AbstractControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.topjava.graduation.TestUtil.*;
import static com.topjava.graduation.data.DishTestData.DISH_MATCHER;
import static com.topjava.graduation.data.DishTestData.getAllByBlueRestaurant;
import static com.topjava.graduation.data.RestaurantTestData.RESTAURANT_ID;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DishRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = DishRestController.REST_URL + '/';

    @Test
    void getLunchMenuByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID)
                .param("date", "2021-04-12")
                .with(userHttpBasic(UserTestData.user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(getAllByBlueRestaurant()));
    }

    @Test
    void getByNullDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID)
                .with(userHttpBasic(UserTestData.user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> Assertions.assertTrue(readListFromJsonMvcResult(result, Dish.class).isEmpty()));
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getByNotFoundRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND)
                .with(userHttpBasic(UserTestData.user)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
