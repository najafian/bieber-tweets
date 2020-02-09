package org.interview.twitter.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import java.util.Date

/**
 * @authors Mehdi Najafian
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class TweetMapper(
        @JsonProperty("id") val messageId: String?,
        @JsonProperty("created_at") val creationDate: Date?,
        @JsonProperty("text") val messageText: String?,
        @JsonProperty("user") val author: AuthorMapper?
) : Comparable<TweetMapper> {
    override operator fun compareTo(oTweetMapper: TweetMapper): Int {
        var result = 0
        if (this !== oTweetMapper)
            result = this.creationDate!!.compareTo(oTweetMapper.creationDate!!)
        return result
    }
}
