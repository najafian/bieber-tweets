package org.interview.twitter.service;


import org.interview.twitter.exceptionsimport.TwitterAuthenticationException;
import org.interview.twitter.model.TwitterRequestDto;
import org.interview.twitter.model.TwitterResponseDto;

import java.util.List;

public interface TwitterService {
    int retrieveAndSaveToDatabase(TwitterRequestDto twitterRequestDto) throws TwitterAuthenticationException;

    List<TwitterResponseDto> findAllTwitterRetrieved();
}
