package com.topjava.graduation.service;

import com.topjava.graduation.model.Vote;
import com.topjava.graduation.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.topjava.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public Vote get(int id) {
        return checkNotFoundWithId(voteRepository.getOne(id), id);
    }

    public void delete(int id) {
        checkNotFoundWithId(voteRepository.delete(id) != 0, id);
    }

    public List<Vote> getAll() {
        return voteRepository.findAll();
    }

    public Vote create(Vote vote) {
        Assert.notNull(vote, "vote must not be null");
        return voteRepository.save(vote);
    }

    public void update(Vote vote) {
        Assert.notNull(vote, "vote must not be null");
        checkNotFoundWithId(voteRepository.getOne(vote.id()), vote.id());
        voteRepository.save(vote);
    }
}
