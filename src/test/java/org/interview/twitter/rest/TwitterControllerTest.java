package org.interview.twitter.rest;

import org.interview.twitter.exception.TwitterAuthenticationException;
import org.interview.twitter.model.*;
import org.interview.twitter.service.TwitterService;
import org.interview.twitter.webservice.rest.TwitterController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.platform.runner.JUnitPlatform;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class TwitterControllerTest {

    @Value("${twitter.keys.consumerKey}")
    private String consumerKey;

    @Value("${twitter.keys.consumerSecret}")
    private String consumerSecret;

    @Value("${twitter.keys.appName}")
    private String appName;

    @InjectMocks
    TwitterController twitterController;

    @Mock
    TwitterService twitterService;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Find All Twitter Retrieved From H2 Database")
    void testFindAllTwitterRetrieved() {
        List<TwitterResponseDto> twitterMessage = new ArrayList<>();
        twitterMessage.add(new TwitterResponseDto(1l, "mehdi", new Date(), "mehdi", 1l, "mehdi najafian", new Date()));
        twitterMessage.add(new TwitterResponseDto(2l, "mehdi2", new Date(), "mehdi2", 2l, "mehdi najafian", new Date()));
        Mockito.when(twitterService.findAllTwitterRetrieved()).thenReturn(twitterMessage);
        List<TwitterResponseDto> allPreservedTwitters = twitterController.getAllPreservedTwitters();
        assertTrue("it is retrieved tweets correctly", allPreservedTwitters.size() == 2);
    }

    @Test
    @DisplayName("Generate URI for PinID Method")
    void testGeneratePinUriFromKeys() throws TwitterAuthenticationException {
        Mockito.when(twitterService.generatePinUriFromKeys(consumerKey, consumerSecret)).thenReturn("twitterUrl");
        String pinUri = twitterController.generatePinUriFromKeys(consumerKey, consumerSecret);
        assertTrue("url is valid!", pinUri.contains("twitter"));
    }

    @Test
    @DisplayName("Retrieve List From Twitter And Save tweets into DB")
    void testRetrieveListFromTwitterAndSave() throws TwitterAuthenticationException {
        TwitterRequestDto twitterRequestDto = new TwitterRequestDto(appName, "", consumerKey, consumerSecret, "bieber");
        Mockito.when(twitterService.retrieveAndSaveToDatabase(twitterRequestDto)).thenReturn(100);
        int numberOfTweets = twitterController.retrieveListFromTwitterAndSave(twitterRequestDto);
        assertEquals("number Of Tweets are!", 100, numberOfTweets);
    }
}