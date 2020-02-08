package org.interview.twitter.service;

import java.util.List;

import org.interview.twitter.exception.TwitterAuthenticationException;
import org.interview.twitter.model.TwitterRequestDto;
import org.interview.twitter.model.TwitterResponseDto;
import org.interview.twitter.service.facade.TwitterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class is a Facade Pattern
 * implementation of the logic of project
 *
 * @authors Mehdi Najafian
 */
@Service
public class TwitterFacadeServiceImpl implements TwitterService {
    private TwitterUtils twitterUtils;

    @Autowired
    public TwitterFacadeServiceImpl(TwitterUtils twitterUtils) {
        this.twitterUtils = twitterUtils;
    }

    @Override
    public int retrieveAndSaveToDatabase(TwitterRequestDto twitterRequestDto) throws TwitterAuthenticationException {
        return twitterUtils.retrievedAndSavedTwitters(twitterRequestDto);
    }

    @Override
    public String generatePinUriFromKeys(String consumerKey, String consumerSecurityKey) throws TwitterAuthenticationException {
        return twitterUtils.generateUriFromKeys(consumerKey, consumerSecurityKey);
    }


    @Override
    public List<TwitterResponseDto> findAllTwitterRetrieved() {
        return twitterUtils.findAllTwitterRetrieved();
    }
}
