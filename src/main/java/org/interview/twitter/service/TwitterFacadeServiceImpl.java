package org.interview.twitter.service;

import java.util.List;
import org.interview.twitter.model.TwitterRequestDto;
import org.interview.twitter.model.TwitterResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class TwitterFacadeServiceImpl implements TwitterService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean retrieveAndSaveToDatabase(TwitterRequestDto twitterRequestDto) {
        return false;
    }

    @Override
    public List<TwitterResponseDto> findAllTwitterRetrieved() {
        return null;
    }
}
