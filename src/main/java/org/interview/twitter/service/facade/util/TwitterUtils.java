package org.interview.twitter.service.facade.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import org.interview.twitter.exceptionsimport.TwitterAuthenticationException;
import org.interview.twitter.model.TwitterRequestDto;
import org.interview.twitter.model.TweetMapper;
import org.interview.twitter.service.facade.TwitterMessageService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TwitterUtils {
    private interface CheckValidity  {
        boolean apply();
    }
    private TwitterAuthenticatorUtils twitterAuthenticatorUtils;
    private TwitterMessageService messageService;
    private SimpleDateFormat simpleDateFormat;

    @Autowired
    public TwitterUtils(TwitterAuthenticatorUtils twitterAuthenticatorUtils, TwitterMessageService messageService, SimpleDateFormat simpleDateFormat) {
        this.twitterAuthenticatorUtils = twitterAuthenticatorUtils;
        this.messageService = messageService;
        this.simpleDateFormat = simpleDateFormat;
    }

    public int retrievedAndSavedTwitters(TwitterRequestDto twitterRequestDto) throws TwitterAuthenticationException {
        HttpRequestFactory authenticationInfo = twitterAuthenticatorUtils.getAuthenticationInfo(
                twitterRequestDto.getConsumerKey(),
                twitterRequestDto.getConsumerSecurityKey(),
                twitterRequestDto.getPinID());
        return getStreamAndSaveMessagesIntoDB(authenticationInfo, twitterRequestDto.getKeywordSearch());
    }

    private int getStreamAndSaveMessagesIntoDB(HttpRequestFactory httpRequestFactory, String keywordSearch) {
        try {
            StringBuilder builder = new StringBuilder()
                    .append("https://stream.twitter.com/1.1/statuses/filter.json?")
                    .append("track=")
                    .append(keywordSearch);
            BufferedReader reader = getTwitterStream(httpRequestFactory, builder.toString());
            return saveIntoDatabase(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int saveIntoDatabase(BufferedReader reader) throws IOException {
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
        messageService.saveTwitterMessage(mapperSet);
        return mapperSet.size();
    }

    @NotNull
    private BufferedReader getTwitterStream(HttpRequestFactory httpRequestFactory, String uriFilter) throws IOException {
        HttpRequest request = httpRequestFactory.buildGetRequest(
                new GenericUrl(uriFilter));
        HttpResponse response = request.execute();
        return new BufferedReader(new InputStreamReader(response.getContent()));
    }
}
