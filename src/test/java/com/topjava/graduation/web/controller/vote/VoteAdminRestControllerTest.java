package com.topjava.graduation.web.controller.vote;

import com.topjava.graduation.model.Vote;
import com.topjava.graduation.web.controller.AbstractControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.topjava.graduation.TestUtil.readListFromJsonMvcResult;
import static com.topjava.graduation.TestUtil.userHttpBasic;
import static com.topjava.graduation.data.UserTestData.admin;
import static com.topjava.graduation.data.UserTestData.user;
import static com.topjava.graduation.data.VoteTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteAdminRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteAdminRestController.REST_URL + '/';

    @Test
    void getAllByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "by")
                .param("date", "2021-04-11")
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(vote, vote_2));
    }

    @Test
    void getAllByNullDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "by")
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> Assertions.assertTrue(readListFromJsonMvcResult(result, Vote.class).isEmpty()));
    }

    @Test
    void getAllByDateForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "by")
                .param("date", "2021-04-11")
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
