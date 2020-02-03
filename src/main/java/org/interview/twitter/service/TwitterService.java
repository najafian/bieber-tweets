package org.interview.twitter.service;

import org.interview.twitter.exception.TwitterAuthenticationException;
import org.interview.twitter.model.TwitterRequestDto;
import org.interview.twitter.model.TwitterResponseDto;

import java.util.List;

/**
 * @authors Mehdi Najafian
 */
public interface TwitterService {
    int retrieveAndSaveToDatabase(TwitterRequestDto twitterRequestDto) throws TwitterAuthenticationException;

    String generatePinUriFromKeys(String consumerKey, String consumerSecurityKey) throws TwitterAuthenticationException;

    List<TwitterResponseDto> findAllTwitterRetrieved();
}
