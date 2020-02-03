package org.interview.twitter.configuration

import com.google.api.client.auth.oauth.*
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.web.client.RestTemplate
import java.text.SimpleDateFormat
import java.util.*

/**
 * This class makes some beans and config the twitter application
 *
 * @Author Mehdi
 */
@Configuration
@EnableConfigurationProperties
class TwitterConfiguration {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    fun requestTokenResponse() = OAuthCredentialsResponse()

    @Bean
    fun oAuthAuthorizeTemporaryTokenUrl() = OAuthAuthorizeTemporaryTokenUrl(AUTHORIZE_URL)

    @Bean
    fun oAuthGetTemporaryToken() = OAuthGetTemporaryToken(REQUEST_TOKEN_URL)

    @Bean
    fun oAuthGetAccessToken() = OAuthGetAccessToken(ACCESS_TOKEN_URL)

    @Bean
    fun httpTransport() = NetHttpTransport()

    @Bean
    fun oAuthParameters() = OAuthParameters()

    @Bean
    fun restTemplate() = RestTemplate()

    @Bean
    fun simpleDateFormat() = SimpleDateFormat(DATE_PATTERN, Locale.ENGLISH)

    companion object {
        const val AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize"
        const val REQUEST_TOKEN_URL = "https://api.twitter.com/oauth/request_token"
        const val ACCESS_TOKEN_URL = "https://api.twitter.com/oauth/access_token"
        const val DATE_PATTERN = "EEE MMM dd HH:mm:ss Z yyyy"
    }
}