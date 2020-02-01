package org.interview.twitter.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import java.util.Date

/**
 * Serialize class for readed tweets.
 *
 * @author bleck84
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class TweetMapper(
        @JsonProperty("id") val messageId: String?,
        @JsonProperty("created_at") val creationDate: Date?,
        @JsonProperty("text") val messageText: String?,
        @JsonProperty("user") val author: AuthorMapper?
) : Comparable<Any> {
    override operator fun compareTo(o: Any): Int {
        val EQUAL = 0
        val result: Int
        val oTweetMapper = o as TweetMapper
        if (this === oTweetMapper)
            result = EQUAL
        else
            result = this.creationDate!!.compareTo(oTweetMapper.creationDate!!)
        return result
    }
}
