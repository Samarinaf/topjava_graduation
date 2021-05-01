package com.topjava.graduation.service;

import com.topjava.graduation.TestMatcher;
import com.topjava.graduation.model.Vote;
import com.topjava.graduation.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.topjava.graduation.data.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService voteService;

    @Test
    public void get() {
        VOTE_MATCHER.assertMatch(voteService.get(VOTE_ID), vote);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> voteService.get(TestMatcher.NOT_FOUND));
    }

    @Test
    public void getAll() {
        VOTE_MATCHER.assertMatch(voteService.getAll(), allVotes());
    }

    @Test
    public void delete() {
        voteService.delete(VOTE_ID);
        assertThrows(NotFoundException.class, () -> voteService.get(VOTE_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> voteService.delete(TestMatcher.NOT_FOUND));
    }

    @Test
    public void create() {
        Vote newVote = getNew();
        Vote created = voteService.create(newVote);
        newVote.setId(created.id());
        VOTE_MATCHER.assertMatch(newVote, created);
        VOTE_MATCHER.assertMatch(voteService.get(created.id()), newVote);
    }

    @Test
    public void createNull() {
        assertThrows(IllegalArgumentException.class, () -> voteService.create(null));
    }

    @Test
    public void update() {
        voteService.update(getUpdated());
        VOTE_MATCHER.assertMatch(voteService.get(VOTE_ID), getUpdated());
    }

    @Test
    public void updateNull() {
        assertThrows(IllegalArgumentException.class, () -> voteService.update(null));
    }

    @Test
    public void updateNotFound() {
        Vote updated = getUpdated();
        updated.setId(TestMatcher.NOT_FOUND);
        assertThrows(NotFoundException.class, () -> voteService.update(updated));
    }
}
