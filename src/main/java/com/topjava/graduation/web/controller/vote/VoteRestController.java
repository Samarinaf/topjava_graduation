package com.topjava.graduation.web.controller.vote;

import com.topjava.graduation.model.Vote;
import com.topjava.graduation.service.VoteService;
import com.topjava.graduation.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.topjava.graduation.web.SecurityUtil.authUserId;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    static final String REST_URL = "/votes";
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VoteService voteService;

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        log.info("get vote with id={} for user with id={}", id, authUserId());
        return voteService.get(id, authUserId());
    }

    @GetMapping
    public List<Vote> getAll() {
        log.info("get all votes for user with id={}", authUserId());
        return voteService.getAllByUser(authUserId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete vote with id={} for user with id={}", id, authUserId());
        voteService.delete(id, authUserId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@Valid @RequestBody Vote vote) {
        log.info("create {} for user with id={}", vote, authUserId());
        ValidationUtil.checkNew(vote);
        Vote created = voteService.create(vote, authUserId());
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Vote vote, @PathVariable int id) {
        log.info("update {} with id={} for user with id={}", vote, id, authUserId());
        ValidationUtil.assureIdConsistent(vote, id);
        //TimeExpiredException is thrown if try to update after 11 a.m.
        ValidationUtil.checkTimeIsAvailable();
        voteService.update(vote, authUserId());
    }
}
