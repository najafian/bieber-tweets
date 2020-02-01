package org.interview.twitter.configuration;

import com.google.api.client.auth.oauth.*;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * This class makes some beans and config the twitter application
 *
 * @Author Mehdi
 *
 */
@Configuration
@EnableConfigurationProperties
public class TwitterConfiguration {
    @Bean
    public OAuthHmacSigner oAuthHmacSigner() {
        return new OAuthHmacSigner();
    }

    @Bean
    public OAuthAuthorizeTemporaryTokenUrl oAuthAuthorizeTemporaryTokenUrl() {
        return new OAuthAuthorizeTemporaryTokenUrl(ConstantFields.AUTHORIZE_URL);
    }

    @Bean
    public OAuthGetTemporaryToken oAuthGetTemporaryToken() {
        return new OAuthGetTemporaryToken(ConstantFields.REQUEST_TOKEN_URL);
    }

    @Bean
    public OAuthGetAccessToken oAuthGetAccessToken() {
        return new OAuthGetAccessToken(ConstantFields.ACCESS_TOKEN_URL);
    }

    @Bean
    public HttpTransport httpTransport() {
        return new NetHttpTransport();
    }

    @Bean
    public OAuthParameters oAuthParameters() {
        return new OAuthParameters();
    }
}
