package org.interview.twitter.webservice.rest;

import org.interview.twitter.model.TwitterRequestDto;
import org.interview.twitter.model.TwitterResponseDto;
import org.interview.twitter.service.TwitterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class TwitterController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TwitterService twitterService;

    @PostMapping
    public boolean retrieveListFromTwitterAndSave(@RequestBody TwitterRequestDto requestValues) {
        logger.info("twitter request info:" + requestValues.toString());
        return twitterService.retrieveAndSaveToDatabase(requestValues);
    }

    @GetMapping
    public List<TwitterResponseDto> getAllPreservedTwitters() {
        List<TwitterResponseDto> allTwitterRetrieved = twitterService.findAllTwitterRetrieved();
        logger.info("Number of twitters retrieved from Database: " + allTwitterRetrieved.size());
        return allTwitterRetrieved;
    }
}
