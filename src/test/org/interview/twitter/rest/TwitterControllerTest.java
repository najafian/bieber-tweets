package org.interview.twitter.rest;

import org.interview.twitter.model.TwitterRequestDto;
import org.interview.twitter.service.TwitterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


class TwitterControllerTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private TwitterRequestDto twitterRequestDto;

    @Value("${twitter.keys.consumerKey}")
    private String consumerKey;

    @Value("${twitter.keys.consumerSecret}")
    private String consumerSecret;

    @Autowired
    private TwitterService twitterService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void retrieveListFromTwitterAndSave() {
    }

    @Test
    void getAllPreservedTwitters() {
    }
}