package com.topjava.graduation.web.controller.vote;

import com.topjava.graduation.data.UserTestData;
import com.topjava.graduation.model.Vote;
import com.topjava.graduation.service.VoteService;
import com.topjava.graduation.util.exception.NotFoundException;
import com.topjava.graduation.web.controller.AbstractControllerTest;
import com.topjava.graduation.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalTime;

import static com.topjava.graduation.TestUtil.*;
import static com.topjava.graduation.data.UserTestData.admin;
import static com.topjava.graduation.data.UserTestData.user;
import static com.topjava.graduation.data.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteRestController.REST_URL + '/';

    @Autowired
    private VoteService voteService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_ID)
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(vote));
    }

    @Test
    void getNotOwn() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_ID)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND)
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_ID))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(getAllByUser()));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + VOTE_ID)
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> voteService.get(VOTE_ID, UserTestData.USER_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND)
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deleteNotOwn() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + VOTE_ID)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void create() throws Exception {
        Vote newVote = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user))
                .content(JsonUtil.writeValue(newVote)))
                .andDo(print())
                .andExpect(status().isCreated());

        Vote created = readFromJson(action, Vote.class);
        newVote.setId(created.id());
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(voteService.get(created.id(), UserTestData.USER_ID), newVote);
    }

    @Test
    void createInvalid() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user))
                .content(JsonUtil.writeValue(getInvalid())))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    //If update vote after 11 a.m. -> TimeExpiredException is thrown

    @Test
    void update() throws Exception {
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            perform(MockMvcRequestBuilders.put(REST_URL + VOTE_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(getUpdated()))
                    .with(userHttpBasic(user)))
                    .andDo(print())
                    .andExpect(status().isNoContent());
            VOTE_MATCHER.assertMatch(voteService.get(VOTE_ID, UserTestData.USER_ID), getUpdated());
        } else {
            perform(MockMvcRequestBuilders.put(REST_URL + VOTE_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(getUpdated()))
                    .with(userHttpBasic(user)))
                    .andDo(print())
                    .andExpect(status().isNotAcceptable());
        }
    }

    @Test
    void updateNotOwn() throws Exception {
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            perform(MockMvcRequestBuilders.put(REST_URL + VOTE_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(getUpdated()))
                    .with(userHttpBasic(admin)))
                    .andDo(print())
                    .andExpect(status().isUnprocessableEntity());
        }
    }

    @Test
    void updateInvalid() throws Exception {
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            Vote invalid = getInvalid();
            invalid.setId(VOTE_ID);
            perform(MockMvcRequestBuilders.put(REST_URL + VOTE_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(invalid))
                    .with(userHttpBasic(user)))
                    .andDo(print())
                    .andExpect(status().isUnprocessableEntity());
        }
    }
}
