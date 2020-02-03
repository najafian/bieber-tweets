package org.interview.twitter.rest;

import org.interview.twitter.exception.TwitterAuthenticationException;
import org.interview.twitter.model.TwitterRequestDto;
import org.interview.twitter.service.facade.TwitterAuthenticatorUtils;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    private HttpHeaders headers;
    private URI uri;
    @Value("${twitter.keys.consumerKey}")
    private String consumerKey;

    @Value("${twitter.keys.consumerSecret}")
    private String consumerSecret;

    @Value("${twitter.keys.appName}")
    private String appName;

    private RestTemplate restTemplate;
    private TwitterAuthenticatorUtils twitterAuthenticatorUtils;

    @Autowired
    public TwitterControllerTest(RestTemplate restTemplate,
                                 TwitterAuthenticatorUtils twitterAuthenticatorUtils) {
        this.restTemplate = restTemplate;
        this.twitterAuthenticatorUtils = twitterAuthenticatorUtils;
    }

    @BeforeEach
    void setUp() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
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
    @DisplayName("Generate URI for PinID Method")
    void testGeneratePinUriFromKeys() {
        StringBuilder params = new StringBuilder()
                .append(uri.toString())
                .append("GenerateUrl")
                .append("?consumerKey=")
                .append(consumerKey)
                .append("&consumerSecretKey=")
                .append(consumerSecret);

        ResponseEntity<String> responseEntity = this.restTemplate.exchange(
                uri.resolve(params.toString()),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class);
        String body = responseEntity.getBody();
        assertNotEquals("Number of tweet is not equal to -1", -1, body);
    }

    /**
     * It is not possible with 2 level Authentication in Twitter website to test with JUnit
     *
     * Only We should test it via Client-Side
     */
    @Test
    void testRetrieveListFromTwitterAndSave() throws TwitterAuthenticationException {
        HttpEntity<?> httpEntity = new HttpEntity<Object>(twitterRequestDto, headers);
        ResponseEntity<Integer> responseEntity = this.restTemplate.exchange(
                uri,
                HttpMethod.PUT,
                httpEntity,
                Integer.class);
        Integer numberOfBieberTweets = responseEntity.getBody();
        logger.info("Number of bieber tweets are: " + numberOfBieberTweets);
        assertNotEquals("Number of tweet is not equal to -1", -1, numberOfBieberTweets);
    }


    @Test
    void getAllPreservedTwitters() {
        ResponseEntity<List> listResponseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity(headers),
                List.class);
        logger.info("Number of twitters retrieved from Database: \n" + listResponseEntity.getBody());
        assertTrue("There are Some twitter messages in Database", listResponseEntity.getBody()!=null);
    }
}