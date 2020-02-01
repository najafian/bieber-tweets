package org.interview.twitter.webservice.rest;

import org.interview.twitter.exceptionsimport.TwitterAuthenticationException;
import org.interview.twitter.model.TwitterRequestDto;
import org.interview.twitter.model.TwitterResponseDto;
import org.interview.twitter.service.TwitterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api")
public class TwitterController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TwitterService twitterService;

    @PostMapping
    public int retrieveListFromTwitterAndSave(@RequestBody TwitterRequestDto requestValues) throws TwitterAuthenticationException {
        logger.info("twitter request info:" + requestValues.toString());
        return twitterService.retrieveAndSaveToDatabase(requestValues);
    }

    @GetMapping
    public List<TwitterResponseDto> getAllPreservedTwitters() {
//        List<TwitterResponseDto> allTwitterRetrieved = twitterService.findAllTwitterRetrieved();
//        return allTwitterRetrieved;
        return new ArrayList<>();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public TwitterAuthenticationException getException(Exception e) {
        return new TwitterAuthenticationException(e.getMessage());
    }
}
