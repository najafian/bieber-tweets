package org.interview.twitter.service.facade;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import org.interview.twitter.exception.TwitterAuthenticationException;
import org.interview.twitter.model.*;
import org.interview.twitter.repository.TwitterMessageRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * This class give all services for
 * Save into DB,
 * Show Result,
 * Sort list of Tweets
 * etc.
 *
 * @authors Mehdi Najafian
 */
@Service
public class TwitterUtils {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private TwitterAuthenticatorUtils twitterAuthenticatorUtils;
    private TwitterDBService twitterDBService;
    private SimpleDateFormat simpleDateFormat;


    @Autowired
    public TwitterUtils(TwitterAuthenticatorUtils twitterAuthenticatorUtils, TwitterDBService messageService, SimpleDateFormat simpleDateFormat, TwitterMessageRepository twitterMessageRepository) {
        this.twitterAuthenticatorUtils = twitterAuthenticatorUtils;
        this.twitterDBService = messageService;
        this.simpleDateFormat = simpleDateFormat;
    }

    public int retrievedAndSavedTwitters(TwitterRequestDto twitterRequestDto) throws TwitterAuthenticationException {
        twitterAuthenticatorUtils.retrieveAuthenticationPinID(twitterRequestDto);
        logger.info("twitter request info:" + twitterRequestDto.toString());
        HttpRequestFactory authenticationInfo = twitterAuthenticatorUtils.getAuthorizedHttpRequestFactory(twitterRequestDto);
        return getStreamAndSaveMessagesIntoDB(authenticationInfo, twitterRequestDto.getKeywordSearch());
    }

    public String generateUriFromKeys(String consumerKey, String consumerSecretKey) throws TwitterAuthenticationException {
        return twitterAuthenticatorUtils.makeUriFromKeys(consumerKey, consumerSecretKey);
    }

    /**
     * Two way to get sorted list:
     *    2- sorted by java Stream  (First choice for this example)
     *    1- sorted directly from database (Better choice)
     * @return sorted list of tweets
     */
    public List<TwitterResponseDto> findAllTwitterRetrieved() {
//        return wrapTwitterMessageDto(twitterMessageRepository.findAllSortedTweets()); //needs only wrap to Dto
        return wrapAndSortTwitterMessageDto(twitterDBService.findAllNotSortedTweets());
    }

    private List<TwitterResponseDto> wrapAndSortTwitterMessageDto(List<TwitterMessage> tweets) {
        return tweets
                .parallelStream()
                .sorted(Comparator.comparing(TwitterMessage::getAuthor))
                .sorted(TwitterMessage::compareTo)
                .map(m -> new TwitterResponseDto(
                        m.getAuthor().getUserId(),
                        m.getAuthor().getName(),
                        m.getAuthor().getCreationDate(),
                        m.getAuthor().getScreenName(),
                        m.getMessageId(),
                        m.getMessageText(),
                        m.getCreationDate()))
                .collect(Collectors.toList());
    }


    private int getStreamAndSaveMessagesIntoDB(HttpRequestFactory httpRequestFactory, String keywordSearch) {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("https://stream.twitter.com/1.1/statuses/filter.json?");
            builder.append("track=");
            builder.append(keywordSearch);
            BufferedReader reader = getTwitterStream(httpRequestFactory, builder.toString());
            return saveTweetsIntoDatabase(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int saveTweetsIntoDatabase(BufferedReader reader) throws IOException {
        long startTime = System.currentTimeMillis();
        List<TweetMapper> mapperSet = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(simpleDateFormat);
        AtomicReference<String> line = new AtomicReference<>("");
        CheckValidity checkValidity = () -> {
            try {
                line.set(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return line.get() != null &&
                    (System.currentTimeMillis() - startTime < 30000) && mapperSet.size() < 100;
        };
        while (checkValidity.apply())
            mapperSet.add(mapper.readValue(line.get(), TweetMapper.class));
        reader.close();
        twitterDBService.saveTwitterMessage(mapperSet);
        return mapperSet.size();
    }

    @NotNull
    private BufferedReader getTwitterStream(HttpRequestFactory httpRequestFactory, String uriFilter) throws IOException {
        HttpRequest request = httpRequestFactory.buildGetRequest(
                new GenericUrl(uriFilter));
        HttpResponse response = request.execute();
        return new BufferedReader(new InputStreamReader(response.getContent()));
    }

    private interface CheckValidity {
        boolean apply();
    }
}
