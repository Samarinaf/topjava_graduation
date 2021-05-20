package com.topjava.graduation.web.controller.vote;

import com.topjava.graduation.model.Vote;
import com.topjava.graduation.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = VoteAdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteAdminRestController {
    static final String REST_URL = "/admin/votes";
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VoteService voteService;

    @GetMapping("/by")
    public List<Vote> getAllByDate(@RequestParam(value = "date", required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDate required = date == null ? LocalDate.now() : date;
        log.info("get all votes by date={}", required);
        return voteService.getAllByDate(required);
    }
}
