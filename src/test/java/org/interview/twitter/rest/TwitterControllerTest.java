package org.interview.twitter.rest;

import org.interview.twitter.exception.TwitterAuthenticationException;
import org.interview.twitter.model.*;
import org.interview.twitter.service.TwitterService;
import org.interview.twitter.service.facade.TwitterDBService;
import org.interview.twitter.webservice.rest.TwitterController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.platform.runner.JUnitPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@SpringBootTest
class TwitterControllerTest {
    private List<TwitterResponseDto> twitterMessage;
    private List<TweetMapper> tweetMappers;
    @Value("${twitter.keys.consumerKey}")
    private String consumerKey;

    @Value("${twitter.keys.consumerSecret}")
    private String consumerSecret;

    @Value("${twitter.keys.appName}")
    private String appName;

    @Autowired
    TwitterController twitterController;

    @Autowired
    private TwitterDBService dbService;

    @Mock
    TwitterService twitterService;

    @InjectMocks
    TwitterController injectedTwitterController;


    @BeforeEach
    void setUp() {
        Date creationDate = new Date();
        tweetMappers = new ArrayList<>();
        TweetMapper tweetMapper = new TweetMapper("1", creationDate, "tweet text", new AuthorMapper("1", creationDate, "mehdi", "mehdiScreenName"));
        tweetMappers.add(tweetMapper);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Find All Twitter Retrieved From H2 Database")
    void testFindAllTwitterRetrieved() {
        dbService.saveTwitterMessage(tweetMappers);
        List<TwitterResponseDto> allPreservedTwitters = twitterController.getAllPreservedTwitters();
        assertTrue("it is retrieved tweets correctly", allPreservedTwitters.size() == 1);
    }

    @Test
    @DisplayName("Generate URI for PinID Method")
    void testGeneratePinUriFromKeys() throws TwitterAuthenticationException {
        String pinUri = twitterController.generatePinUriFromKeys(consumerKey, consumerSecret);
        assertTrue("the twitter url for PinID is correctly created", pinUri.contains("api.twitter.com"));
    }

    @Test
    @DisplayName("Retrieve List From Twitter And Save tweets into DB")
    void testRetrieveListFromTwitterAndSave() throws TwitterAuthenticationException {
        TwitterRequestDto twitterRequestDto = new TwitterRequestDto(appName, "", consumerKey, consumerSecret, "bieber");
        Mockito.when(twitterService.retrieveAndSaveToDatabase(twitterRequestDto)).thenReturn(100);
        int numberOfTweets = injectedTwitterController.retrieveListFromTwitterAndSave(twitterRequestDto);
        assertEquals("number Of Tweets are!", 100, numberOfTweets);
    }
}