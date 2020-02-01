package org.interview.twitter.rest;

import org.interview.twitter.model.TwitterRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertNotEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TwitterControllerTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private TwitterRequestDto twitterRequestDto;
    private URI uri;
    HttpEntity entity;
    @Value("${twitter.keys.consumerKey}")
    private String consumerKey;

    @Value("${twitter.keys.consumerSecret}")
    private String consumerSecret;

    @Value("${twitter.keys.appName}")
    private String appName;

    @Autowired
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        final String baseUrl = "http://localhost:8080/api/";
        try {
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        twitterRequestDto = new TwitterRequestDto(appName, null, consumerKey, consumerSecret, "Bieber");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void retrieveListFromTwitterAndSave() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(twitterRequestDto, headers);
        ResponseEntity<Integer> responseEntity = this.restTemplate.exchange(
                uri,
                HttpMethod.POST,
                httpEntity,
                Integer.class);
        Integer numberOfBieberTweets = responseEntity.getBody();
        logger.info("Number of bieber tweets are: " + numberOfBieberTweets);
        assertNotEquals("Number of tweet is not equal to -1", -1, numberOfBieberTweets);
    }

    @Test
    void getAllPreservedTwitters() {
        HttpHeaders headers = new HttpHeaders();
        entity = new HttpEntity(headers);
        ResponseEntity<List> listResponseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, entity, List.class);
        logger.info("Number of twitters retrieved from Database: \n" + listResponseEntity.getBody());
        assertTrue("There are Some twitter messages in Database", listResponseEntity.getBody().size() > 0);
    }
}