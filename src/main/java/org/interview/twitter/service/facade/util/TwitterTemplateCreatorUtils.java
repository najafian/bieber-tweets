package org.interview.twitter.service.facade.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

@Component
public class TwitterTemplateCreatorUtils {
    @Autowired
    private Environment env;

    public Twitter getTwitterTemplate() {
        String consumerKey = env.getProperty("twitter.keys.consumerKey");
        String consumerSecret = env.getProperty("twitter.keys.consumerSecret");
        String accessToken = env.getProperty("twitter.keys.accessToken");
        String accessTokenSecret = env.getProperty("twitter.keys.accessTokenSecret");

        TwitterTemplate twitterTemplate =
                new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        twitterTemplate.setRequestFactory((uri, httpMethod) -> null);
        RestTemplate restTemplate = twitterTemplate.getRestTemplate();
        return twitterTemplate;
    }
}