package org.interview.twitter.service.facade;

import org.interview.twitter.model.Author;
import org.interview.twitter.model.AuthorMapper;
import org.interview.twitter.model.TweetMapper;
import org.interview.twitter.model.TwitterMessage;
import org.interview.twitter.repository.TwitterMessageRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Provide access to the H2 database for CRUD
 *
 * @authors Mehdi Najafian
 */
@Service
public class TwitterDBService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TwitterMessageRepository twitterMessageRepository;

    @Transactional
    @Modifying
    public boolean saveTwitterMessage(@NotNull List<TweetMapper> tweetMapper) {
        try{
        tweetMapper.stream()
                .sorted(TweetMapper::compareTo)
                .forEach(item -> {
            TwitterMessage twitterMessage = new TwitterMessage();
            twitterMessage.setMessageId(Long.valueOf(item.getMessageId()));
            twitterMessage.setCreationDate(item.getCreationDate());
            twitterMessage.setMessageText(item.getMessageText());
            twitterMessage.setAuthor(convertAuthorMapper(item.getAuthor()));
            logger.info(twitterMessage.toString());
            twitterMessageRepository.save(twitterMessage);
        });
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return true;
    }

    private Author convertAuthorMapper(AuthorMapper authorMapper) {
        Author author = new Author();
        author.setUserId(Long.valueOf(authorMapper.getUserId()));
        author.setCreationDate(authorMapper.getCreationDate());
        author.setName(authorMapper.getName());
        author.setScreenName(authorMapper.getScreenName());
        return author;
    }


    @Transactional
    public List<TwitterMessage> findAllSortedTweets() {
//        return twitterMessageRepository.findAll(Sort.sort(TwitterMessage.class).by());
        return twitterMessageRepository.findAllSortedTweets();
    }

    @Transactional
    public List<TwitterMessage> findAllNotSortedTweets() {
        return twitterMessageRepository.findAll();
    }
}
