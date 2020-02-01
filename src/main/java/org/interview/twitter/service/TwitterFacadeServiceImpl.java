package org.interview.twitter.service;

import java.util.List;

import org.interview.twitter.exceptionsimport.TwitterAuthenticationException;
import org.interview.twitter.model.TwitterRequestDto;
import org.interview.twitter.model.TwitterResponseDto;
import org.interview.twitter.service.facade.util.TwitterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TwitterFacadeServiceImpl implements TwitterService {
    @Autowired
    private TwitterUtils twitterUtils;

    @Override
    public int retrieveAndSaveToDatabase(TwitterRequestDto twitterRequestDto) throws TwitterAuthenticationException {
       return twitterUtils.retrievedAndSavedTwitters(twitterRequestDto);
    }

    @Override
    public List<TwitterResponseDto> findAllTwitterRetrieved() {
        return null;
    }
}
