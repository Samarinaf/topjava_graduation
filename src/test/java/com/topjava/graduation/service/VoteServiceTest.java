package com.topjava.graduation.service;

import com.topjava.graduation.data.UserTestData;
import com.topjava.graduation.model.Vote;
import com.topjava.graduation.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Arrays;

import static com.topjava.graduation.TestUtil.*;
import static com.topjava.graduation.data.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService voteService;

    @Test
    void get() {
        VOTE_MATCHER.assertMatch(voteService.get(VOTE_ID, UserTestData.USER_ID), vote);
    }

    @Test
    void getNotOwn() {
        assertThrows(NotFoundException.class, () -> voteService.get(VOTE_ID, UserTestData.ADMIN_ID));
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> voteService.get(NOT_FOUND, UserTestData.USER_ID));
    }

    @Test
    void getAll() {
        VOTE_MATCHER.assertMatch(voteService.getAll(), allVotes());
    }

    @Test
    void getAllByUser() {
        VOTE_MATCHER.assertMatch(voteService.getAllByUser(UserTestData.USER_ID), Arrays.asList(vote, vote_1));
    }

    @Test
    void getAllByDate() {
        VOTE_MATCHER.assertMatch(voteService.getAllByDate(LocalDate.of(2021, 4, 11)), Arrays.asList(vote, vote_2));
    }

    @Test
    void delete() {
        voteService.delete(VOTE_ID, UserTestData.USER_ID);
        assertThrows(NotFoundException.class, () -> voteService.get(VOTE_ID, UserTestData.USER_ID));
    }

    @Test
    void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> voteService.delete(VOTE_ID, UserTestData.ADMIN_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> voteService.delete(NOT_FOUND, UserTestData.USER_ID));
    }

    @Test
    void create() {
        Vote newVote = getNew();
        Vote created = voteService.create(newVote, UserTestData.ADMIN_ID);
        newVote.setId(created.id());
        VOTE_MATCHER.assertMatch(newVote, created);
        VOTE_MATCHER.assertMatch(voteService.get(created.id(), UserTestData.ADMIN_ID), newVote);
    }

    @Test
    void createNull() {
        assertThrows(IllegalArgumentException.class, () -> voteService.create(null, UserTestData.USER_ID));
    }

    @Test
    void update() {
        voteService.update(getUpdated(), UserTestData.USER_ID);
        VOTE_MATCHER.assertMatch(voteService.get(VOTE_ID, UserTestData.USER_ID), getUpdated());
    }

    @Test
    void updateNotOwn() {
        assertThrows(IllegalArgumentException.class, () -> voteService.update(vote, UserTestData.ADMIN_ID));
    }

    @Test
    void updateNull() {
        assertThrows(IllegalArgumentException.class, () -> voteService.update(null, UserTestData.USER_ID));
    }

    @Test
    void updateNotFound() {
        Vote updated = getUpdated();
        updated.setId(NOT_FOUND);
        assertThrows(NotFoundException.class, () -> voteService.update(updated, UserTestData.USER_ID));
    }
}
