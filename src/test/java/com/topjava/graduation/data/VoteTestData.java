package com.topjava.graduation.data;

import com.topjava.graduation.TestMatcher;
import com.topjava.graduation.model.AbstractBaseEntity;
import com.topjava.graduation.model.Vote;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class VoteTestData {
    public static final TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Vote.class, "restaurant.lunchMenu", "user.password");

    public static final int VOTE_ID = AbstractBaseEntity.START_SEQ + 16;

    public static final Vote vote = new Vote(VOTE_ID, LocalDate.of(2021, 4, 11), UserTestData.user, RestaurantTestData.restaurant);
    public static final Vote vote_1 = new Vote(VOTE_ID + 1, LocalDate.of(2021, 4, 12), UserTestData.user, RestaurantTestData.restaurant_3);
    public static final Vote vote_2 = new Vote(VOTE_ID + 2, LocalDate.of(2021, 4, 11), UserTestData.admin, RestaurantTestData.restaurant_1);
    public static final Vote vote_3 = new Vote(VOTE_ID + 3, LocalDate.of(2021, 4, 12), UserTestData.admin, RestaurantTestData.restaurant_2);

    public static Vote getNew() {
        return new Vote(null, LocalDate.now(), UserTestData.admin, RestaurantTestData.restaurant);
    }

    public static Vote getUpdated() {
        return new Vote(VOTE_ID, LocalDate.now(), UserTestData.user, RestaurantTestData.restaurant_3);
    }

    public static Vote getInvalid() {
        return new Vote(null, null, null, null);
    }

    public static List<Vote> allVotes() {
        return Arrays.asList(vote, vote_1, vote_2, vote_3);
    }

    public static List<Vote> getAllByUser() {
        return Arrays.asList(vote, vote_1);
    }
}
