package org.interview.twitter.service.facade;

import org.interview.twitter.model.Author;
import org.interview.twitter.model.AuthorMapper;
import org.interview.twitter.model.TweetMapper;
import org.interview.twitter.model.TwitterMessage;
import org.interview.twitter.repository.TwitterMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TwitterMessageService {
    @Autowired
    private TwitterMessageRepository twitterMessageRepository;

    @Transactional
    public boolean saveTwitterMessage(List<TweetMapper> tweetMapper) {
        tweetMapper.stream().forEach(item -> {
            TwitterMessage twitterMessage = new TwitterMessage();
            twitterMessage.setMessageId(Long.valueOf(item.getMessageId()));
            twitterMessage.setCreationDate(item.getCreationDate());
            twitterMessage.setMessageText(item.getMessageText());
            twitterMessage.setAuthor(convertAuthorMapper(item.getAuthor()));
            System.out.println(twitterMessage.toString());
            twitterMessageRepository.save(twitterMessage);
        });
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
    public List<TwitterMessage> findAllTwitterMessages() {
        return twitterMessageRepository.findAll();
    }
}
