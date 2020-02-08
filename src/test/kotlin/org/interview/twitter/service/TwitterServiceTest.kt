package org.interview.twitter.service

import org.interview.twitter.exception.TwitterAuthenticationException
import org.interview.twitter.model.AuthorMapper
import org.interview.twitter.model.TweetMapper
import org.interview.twitter.model.TwitterRequestDto
import org.interview.twitter.model.TwitterResponseDto
import org.interview.twitter.service.facade.TwitterDBService
import org.interview.twitter.webservice.rest.TwitterController
import org.junit.Ignore
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.util.AssertionErrors
import java.util.*

@SpringBootTest
internal class TwitterServiceTest {
    private var twitterMessage: MutableList<TwitterResponseDto>? = null
    private var tweetMappers: MutableList<TweetMapper>? = null
    @Value("\${twitter.keys.consumerKey}")
    private val consumerKey: String? = null
    @Value("\${twitter.keys.consumerSecret}")
    private val consumerSecret: String? = null
    @Value("\${twitter.keys.appName}")
    private val appName: String? = null
    @Autowired
    private val dbService: TwitterDBService? = null
    @Autowired
    private val twitterService: TwitterService? = null
    @Autowired
    var twitterController: TwitterController? = null

    @BeforeEach
    fun setUp() {
        val creationDate = Date()
        twitterMessage = ArrayList()
        tweetMappers = ArrayList()
        twitterMessage!!.add(TwitterResponseDto(1L, "mehdi", creationDate, "mehdiScreenName", 1L, "tweet text", creationDate))
        val tweetMapper = TweetMapper("1", creationDate, "tweet text", AuthorMapper("1", creationDate, "mehdi", "mehdiScreenName"))
        tweetMappers!!.add(tweetMapper)
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @Throws(TwitterAuthenticationException::class)
    fun findAllTwitterRetrieved() {
        twitterController!!.generatePinUriFromKeys(consumerKey, consumerSecret)
        dbService!!.saveTwitterMessage(tweetMappers!!)
        AssertionErrors.assertTrue("it is retrieved tweets correctly", twitterService!!.findAllTwitterRetrieved().size == twitterMessage!!.size)
    }

    @Test
    @Throws(TwitterAuthenticationException::class)
    fun testGeneratePinUriFromKeys() {
        val url = twitterService!!.generatePinUriFromKeys(consumerKey, consumerSecret)
        AssertionErrors.assertTrue("the twitter url for PinID is correctly created", url.contains("api.twitter.com"))
    }

    @Ignore
    @Throws(TwitterAuthenticationException::class)
    fun testRetrieveAndSaveToDatabase() {
        val dto = TwitterRequestDto(appName, "dynamicPinID", consumerKey, consumerSecret, "bieber")
        AssertionErrors.assertTrue("it is retrieved and save it into database", twitterService!!.retrieveAndSaveToDatabase(dto) == 100)
    }
}