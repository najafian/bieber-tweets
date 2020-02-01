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
package org.interview.twitter.service.facade.util;

import com.google.api.client.auth.oauth.*;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import org.interview.twitter.exceptionsimport.TwitterAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Scanner;

/**
 * Provide access to the Twitter API by implementing the required OAuth flow
 *
 * @authors Carlo Sciolla, Mehdi
 */
@Service
public class TwitterAuthenticatorUtils {

    private String consumerKey;
    private String consumerSecret;
    private String providedPin;

    private HttpTransport TRANSPORT;
    private OAuthGetAccessToken accessToken;
    private OAuthHmacSigner oAuthHmacSigner;
    private OAuthAuthorizeTemporaryTokenUrl authorizeUrl;
    private OAuthGetTemporaryToken requestToken;
    private OAuthParameters parameters;
    private HttpRequestFactory factory;

    @Autowired
    public TwitterAuthenticatorUtils(HttpTransport TRANSPORT,
                                     OAuthGetAccessToken accessToken,
                                     OAuthHmacSigner oAuthHmacSigner,
                                     OAuthAuthorizeTemporaryTokenUrl authorizeUrl,
                                     OAuthGetTemporaryToken requestToken,
                                     OAuthParameters parameters) {
        this.TRANSPORT = TRANSPORT;
        this.accessToken = accessToken;
        this.oAuthHmacSigner = oAuthHmacSigner;
        this.authorizeUrl = authorizeUrl;
        this.requestToken = requestToken;
        this.parameters = parameters;
    }

    /**
     * Create a new TwitterAuthenticator
     *
     * @param consumerKey    The OAuth consumer key
     * @param consumerSecret The OAuth consumer secret
     */
    public HttpRequestFactory getAuthenticationInfo(String consumerKey,
                                                    String consumerSecret,
                                                    String providedPin
    ) throws TwitterAuthenticationException {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.providedPin = providedPin;
        return getAuthorizedHttpRequestFactory();
    }

    /**
     * Lazily initialize an HTTP request factory which embeds the OAuth tokens required by the Twitter APIs
     *
     * @return The authenticated HTTP request factory
     */
    public synchronized HttpRequestFactory getAuthorizedHttpRequestFactory() throws TwitterAuthenticationException {
        if (factory != null) {
            return factory;
        }
        factory = createRequestFactory();
        return factory;
    }


    /**
     * Create a new authenticated HTTP request factory which embeds the OAuth tokens required by the Twitter APIs
     *
     * @return The authenticated HTTP request factory
     */
    private HttpRequestFactory createRequestFactory() throws TwitterAuthenticationException {
        oAuthHmacSigner.clientSharedSecret = consumerSecret;
        OAuthCredentialsResponse requestTokenResponse = getTemporaryToken(oAuthHmacSigner);
        oAuthHmacSigner.tokenSharedSecret = requestTokenResponse.tokenSecret;
        authorizeUrl.temporaryToken = requestTokenResponse.token;
        retrievePin(authorizeUrl);

        OAuthCredentialsResponse accessTokenResponse = retrieveAccessTokens(providedPin, oAuthHmacSigner, requestTokenResponse.token);
        oAuthHmacSigner.tokenSharedSecret = accessTokenResponse.tokenSecret;

        parameters.consumerKey = consumerKey;
        parameters.token = accessTokenResponse.token;
        parameters.signer = oAuthHmacSigner;

        return TRANSPORT.createRequestFactory(parameters);
    }

    /**
     * Retrieve the initial temporary tokens required to obtain the acces token
     *
     * @param signer The HMAC signer used to cryptographically sign requests to Twitter
     * @return The response containing the temporary tokens
     */
    private OAuthCredentialsResponse getTemporaryToken(final OAuthHmacSigner signer) throws TwitterAuthenticationException {

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
     * Guide the user to obtain a PIN from twitter to authorize the requests
     *
     * @param authorizeUrl The URL embedding the temporary tokens to be used to request the PIN
     * @return The PIN as it is entered by the user after following the Twitter OAuth wizard
     */
    private void retrievePin(final OAuthAuthorizeTemporaryTokenUrl authorizeUrl) throws TwitterAuthenticationException {
        if (providedPin == null)
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("Go to the following link in your browser:\n" + authorizeUrl.build());
                System.out.println("\nPlease enter the retrieved PIN:");
                providedPin = scanner.nextLine();
            }
        if (providedPin == null) {
            throw new TwitterAuthenticationException("Unable to read entered PIN");
        }
    }


    /**
     * Exchange the temporary token and the PIN for an access token that can be used to invoke Twitter APIs
     *
     * @param providedPin The PIN that the user obtained when following the Twitter OAuth wizard
     * @param signer      The HMAC signer used to cryptographically sign requests to Twitter
     * @param token       The temporary token to be exchanged for the access token
     * @return The access token that can be used to invoke Twitter APIs
     */
    private OAuthCredentialsResponse retrieveAccessTokens(final String providedPin, final OAuthHmacSigner signer, final String token) throws TwitterAuthenticationException {
        accessToken.verifier = providedPin;
        accessToken.consumerKey = consumerSecret;
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
