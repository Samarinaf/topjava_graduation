package com.topjava.graduation.service;

import com.topjava.graduation.TestMatcher;
import com.topjava.graduation.data.UserTestData;
import com.topjava.graduation.model.Vote;
import com.topjava.graduation.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Arrays;

import static com.topjava.graduation.data.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService voteService;

    @Test
    public void get() {
        VOTE_MATCHER.assertMatch(voteService.get(VOTE_ID, UserTestData.USER_ID), vote);
    }

    @Test
    public void getNotOwn() {
        assertThrows(NotFoundException.class, () -> voteService.get(VOTE_ID, UserTestData.ADMIN_ID));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> voteService.get(TestMatcher.NOT_FOUND, UserTestData.USER_ID));
    }

    @Test
    public void getAll() {
        VOTE_MATCHER.assertMatch(voteService.getAll(), allVotes());
    }

    @Test
    public void getAllByUser() {
        VOTE_MATCHER.assertMatch(voteService.getAllByUser(UserTestData.USER_ID), Arrays.asList(vote, vote_1));
    }

    @Test
    public void getAllByDate() {
        VOTE_MATCHER.assertMatch(voteService.getAllByDate(LocalDate.of(2021, 4, 11)), Arrays.asList(vote, vote_2));
    }

    @Test
    public void delete() {
        voteService.delete(VOTE_ID, UserTestData.USER_ID);
        assertThrows(NotFoundException.class, () -> voteService.get(VOTE_ID, UserTestData.USER_ID));
    }

    @Test
    public void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> voteService.delete(VOTE_ID, UserTestData.ADMIN_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> voteService.delete(TestMatcher.NOT_FOUND, UserTestData.USER_ID));
    }

    @Test
    public void create() {
        Vote newVote = getNew();
        Vote created = voteService.create(newVote, UserTestData.ADMIN_ID);
        newVote.setId(created.id());
        VOTE_MATCHER.assertMatch(newVote, created);
        VOTE_MATCHER.assertMatch(voteService.get(created.id(), UserTestData.ADMIN_ID), newVote);
    }

    @Test
    public void createNull() {
        assertThrows(IllegalArgumentException.class, () -> voteService.create(null, UserTestData.USER_ID));
    }

    @Test
    public void update() {
        voteService.update(getUpdated(), UserTestData.USER_ID);
        VOTE_MATCHER.assertMatch(voteService.get(VOTE_ID, UserTestData.USER_ID), getUpdated());
    }

    @Test
    public void updateNotOwn() {
        assertThrows(IllegalArgumentException.class, () -> voteService.update(vote, UserTestData.ADMIN_ID));
    }

    @Test
    public void updateNull() {
        assertThrows(IllegalArgumentException.class, () -> voteService.update(null, UserTestData.USER_ID));
    }

    @Test
    public void updateNotFound() {
        Vote updated = getUpdated();
        updated.setId(TestMatcher.NOT_FOUND);
        assertThrows(NotFoundException.class, () -> voteService.update(updated, UserTestData.USER_ID));
    }
}
