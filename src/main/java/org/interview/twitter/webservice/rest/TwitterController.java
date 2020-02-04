package org.interview.twitter.webservice.rest;

import org.interview.twitter.exception.TwitterAuthenticationException;
import org.interview.twitter.model.TwitterRequestDto;
import org.interview.twitter.model.TwitterResponseDto;
import org.interview.twitter.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @authors Mehdi Najafian
 */
@RestController
@RequestMapping(value = "/api")
public class TwitterController {


    @Autowired
    private TwitterService twitterService;

    @GetMapping
    @RequestMapping(value = "/GenerateUrl")
    public String generatePinUriFromKeys(@RequestParam("consumerKey") String consumerKey,
                                         @RequestParam("consumerSecretKey") String consumerSecretKey)
            throws TwitterAuthenticationException {
        return twitterService.generatePinUriFromKeys(consumerKey, consumerSecretKey);
    }

    @PutMapping
    public int retrieveListFromTwitterAndSave(@RequestBody TwitterRequestDto requestValues)
            throws TwitterAuthenticationException {
        return twitterService.retrieveAndSaveToDatabase(requestValues);
    }

    @GetMapping
    public List<TwitterResponseDto> getAllPreservedTwitters() {
        return twitterService.findAllTwitterRetrieved();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public TwitterAuthenticationException getException(Exception e) {
        return new TwitterAuthenticationException(e.getMessage());
    }
}
