package com.topjava.graduation.web.controller.vote;

import com.topjava.graduation.model.Vote;
import com.topjava.graduation.service.VoteService;
import com.topjava.graduation.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    static final String REST_URL = "/votes";
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VoteService voteService;

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        log.info("get vote by id={}", id);
        return voteService.get(id);
    }

    @GetMapping
    public List<Vote> getAll() {
        log.info("get all votes");
        return voteService.getAll();
    }

    @GetMapping("/by")
    public List<Vote> getAllByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get all votes by date={}", date);
        return voteService.getAllByDate(date);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete vote with id={}", id);
        voteService.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@Valid @RequestBody Vote vote) {
        log.info("create {}", vote);
        ValidationUtil.checkNew(vote);
        Vote created = voteService.create(vote);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Vote vote, @PathVariable int id) {
        log.info("update {} with id={}", vote, id);
        ValidationUtil.assureIdConsistent(vote, id);
        //TimeExpiredException is thrown if try to update after 11 a.m.
        ValidationUtil.checkTimeIsAvailable();
        voteService.update(vote);
    }
}
