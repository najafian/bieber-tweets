package org.interview.twitter.service.facade;

import org.interview.twitter.model.TwitterMessage;
import org.interview.twitter.repository.TwitterMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TwitterMessageService {
    @Autowired
    private TwitterMessageRepository twitterMessageRepository;

    @Transactional
    public boolean saveTwitterMessage() {
        TwitterMessage twitterMessage = new TwitterMessage();
        twitterMessage.setMessageId(1l);
        twitterMessage.setCreationDate(new Date());
        twitterMessageRepository.save(twitterMessage);
        return true;
    }

    @Transactional
    public List<TwitterMessage> findAllTwitterMessages() {
        return twitterMessageRepository.findAll();
    }
}
