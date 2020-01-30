package org.interview.twitter.service;


import org.interview.twitter.model.TwitterRequestDto;
import org.interview.twitter.model.TwitterResponseDto;

import java.util.List;

public interface TwitterService {
    boolean retrieveAndSaveToDatabase(TwitterRequestDto twitterRequestDto);

    List<TwitterResponseDto> findAllTwitterRetrieved();
}
