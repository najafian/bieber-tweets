/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * ________  __    __  ________    ____       ______   *
 * /_/_/_/_/ /_/   /_/ /_/_/_/_/  _/_/_/_   __/_/_/_/   *
 * /_/_____  /_/___/_/    /_/    /_/___/_/  /_/          *
 * /_/_/_/_/   /_/_/_/    /_/    /_/_/_/_/  /_/           *
 * ______/_/       /_/    /_/    /_/   /_/  /_/____        *
 * /_/_/_/_/       /_/    /_/    /_/   /_/    /_/_/_/ . io  *
 * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package org.interview.twitter.service.facade;

import com.google.api.client.auth.oauth.*;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import org.interview.twitter.exception.TwitterAuthenticationException;
import org.interview.twitter.model.TwitterRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Provide access to the Twitter API by implementing the required OAuth flow
 *
 * @authors Carlo Sciolla, Mehdi Najafian
 */
@Service
public class TwitterAuthenticatorUtils {
    private HttpTransport TRANSPORT;
    private OAuthGetAccessToken accessToken;
    private OAuthHmacSigner oAuthHmacSigner;
    private OAuthAuthorizeTemporaryTokenUrl authorizeUrl;
    private OAuthGetTemporaryToken requestToken;
    private OAuthParameters parameters;
    private HttpRequestFactory factory;
    private OAuthCredentialsResponse requestTokenResponse;

    @Autowired
    public TwitterAuthenticatorUtils(HttpTransport TRANSPORT,
                                     OAuthGetAccessToken accessToken,
                                     OAuthAuthorizeTemporaryTokenUrl authorizeUrl,
                                     OAuthGetTemporaryToken requestToken,
                                     OAuthParameters parameters,
                                     OAuthCredentialsResponse requestTokenResponse) {
        this.TRANSPORT = TRANSPORT;
        this.accessToken = accessToken;
        this.authorizeUrl = authorizeUrl;
        this.requestToken = requestToken;
        this.parameters = parameters;
        this.requestTokenResponse = requestTokenResponse;
    }


    /**
     * Create a new TwitterAuthenticator
     *
     * @param twitterRequestDto
     * @throws TwitterAuthenticationException
     */
    public void retrieveAuthenticationPinID(TwitterRequestDto twitterRequestDto)
            throws TwitterAuthenticationException {
        String AuthorizedUri = makeUriFromKeys(twitterRequestDto.getConsumerKey(), twitterRequestDto.getConsumerSecurityKey());
        String generatedPinID = twitterRequestDto.getPinID()==null? retrievePin(AuthorizedUri):twitterRequestDto.getPinID();
        twitterRequestDto.setPinID(generatedPinID);
    }

    /**
     * Lazily initialize an HTTP request factory which embeds the OAuth tokens required by the Twitter APIs
     *
     * @return The authenticated HTTP request factory
     */
    public synchronized HttpRequestFactory getAuthorizedHttpRequestFactory(TwitterRequestDto twitterRequestDto) throws TwitterAuthenticationException {
        if (factory != null) {
            return factory;
        }
        factory = createRequestFactory(twitterRequestDto);
        return factory;
    }


    /**
     * Create a new authenticated HTTP request factory which embeds the OAuth tokens required by the Twitter APIs
     *
     * @return The authenticated HTTP request factory
     */
    private HttpRequestFactory createRequestFactory(TwitterRequestDto twitterRequestDto) throws TwitterAuthenticationException {
        OAuthCredentialsResponse accessTokenResponse = retrieveAccessTokens(twitterRequestDto, oAuthHmacSigner, requestTokenResponse.token);
        oAuthHmacSigner.tokenSharedSecret = accessTokenResponse.tokenSecret;
        parameters.consumerKey = twitterRequestDto.getConsumerKey();
        parameters.token = accessTokenResponse.token;
        parameters.signer = oAuthHmacSigner;
        return TRANSPORT.createRequestFactory(parameters);
    }


    public String makeUriFromKeys(String consumerKey, String consumerSecretKey) throws TwitterAuthenticationException {
        oAuthHmacSigner = new OAuthHmacSigner();
        oAuthHmacSigner.clientSharedSecret = consumerSecretKey;
        requestTokenResponse = getTemporaryToken(oAuthHmacSigner, consumerKey);
        oAuthHmacSigner.tokenSharedSecret = requestTokenResponse.tokenSecret;
        authorizeUrl.temporaryToken = requestTokenResponse.token;
        return authorizeUrl.build();
    }

    /**
     * Retrieve the initial temporary tokens required to obtain the acces token
     *
     * @param signer The HMAC signer used to cryptographically sign requests to Twitter
     * @return The response containing the temporary tokens
     */
    private OAuthCredentialsResponse getTemporaryToken(final OAuthHmacSigner signer, String consumerKey) throws TwitterAuthenticationException {

        requestToken.consumerKey = consumerKey;
        requestToken.transport = TRANSPORT;
        requestToken.signer = signer;
        OAuthCredentialsResponse requestTokenResponse;
        try {
            requestTokenResponse = requestToken.execute();
        } catch (IOException e) {
            throw new TwitterAuthenticationException("Unable to aquire temporary token: " + e.getMessage(), e);
        }
        System.out.println("Aquired temporary token...\n");
        return requestTokenResponse;
    }

    /**
     * This method is only for test in JUnit
     * Guide the user to obtain a PIN from twitter to authorize the requests
     *
     * @param authorizeUrl The URL embedding the temporary tokens to be used to request the PIN
     * @return The PIN as it is entered by the user after following the Twitter OAuth wizard
     */
    private String retrievePin(final String authorizeUrl) throws TwitterAuthenticationException {
        String providedPin = null;
        try (BufferedReader b = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Go to the following link in your browser:\n" + authorizeUrl);
            System.out.println("\nPlease enter the retrieved PIN:");
            providedPin = b.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (providedPin == null) {
            throw new TwitterAuthenticationException("Unable to read entered PIN");
        }
        return providedPin;
    }

    /**
     * Exchange the temporary token and the PIN for an access token that can be used to invoke Twitter APIs
     *
     * @param twitterRequestDto
     * @param signer            The HMAC signer used to cryptographically sign requests to Twitter
     * @param token             The temporary token to be exchanged for the access token
     * @return The access token that can be used to invoke Twitter APIs
     * @throws TwitterAuthenticationException
     */
    private OAuthCredentialsResponse retrieveAccessTokens(TwitterRequestDto twitterRequestDto,
                                                          final OAuthHmacSigner signer,
                                                          final String token) throws TwitterAuthenticationException {
        accessToken.verifier = twitterRequestDto.getPinID();
        accessToken.consumerKey = twitterRequestDto.getConsumerKey();
        accessToken.signer = signer;
        accessToken.transport = TRANSPORT;
        accessToken.temporaryToken = token;
        OAuthCredentialsResponse accessTokenResponse;
        try {
            accessTokenResponse = accessToken.execute();
        } catch (IOException e) {
            throw new TwitterAuthenticationException("Unable to authorize access: " + e.getMessage(), e);
        }
        System.out.println("\nAuthorization was successful");

        return accessTokenResponse;
    }

}
