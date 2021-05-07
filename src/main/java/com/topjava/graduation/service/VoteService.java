package com.topjava.graduation.service;

import com.topjava.graduation.model.Vote;
import com.topjava.graduation.repository.UserRepository;
import com.topjava.graduation.repository.VoteRepository;
import com.topjava.graduation.util.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.topjava.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    public VoteService(VoteRepository voteRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
    }

    public Vote get(int id, int userId) {
        return checkNotFoundWithId(voteRepository.findByIdAndUser(id, userId).orElse(null), id);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(voteRepository.delete(id, userId) != 0, id);
    }

    public List<Vote> getAll() {
        return voteRepository.findAll();
    }

    public List<Vote> getAllByUser(int userId) {
        return voteRepository.findAllByUser(userId);
    }

    public List<Vote> getAllByDate(LocalDate date) {
        return voteRepository.findAllByDate(date);
    }

    public Vote create(Vote vote, int userId) {
        Assert.notNull(vote, "vote must not be null");
        if (vote.getUser() != null && vote.getUser().id() != userId) return null;
        vote.setUser(userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with id=" + userId + " not found")));
        return voteRepository.save(vote);
    }

    public void update(Vote vote, int userId) {
        Assert.notNull(vote, "vote must not be null");
        checkNotFoundWithId(voteRepository.findById(vote.id()).orElse(null), vote.id());
        if (vote.getUser() == null || vote.getUser().id() != userId) {
            throw new IllegalArgumentException("Vote doesn't belong to user with id=" + userId);
        }
        voteRepository.save(vote);
    }
}
