package org.interview.twitter.model

import com.fasterxml.jackson.annotation.JsonProperty

import java.util.Date

/**
 * This is a Dto class
 *
 * @Author Mehdi
 */
class TwitterResponseDto  {

    @get:JsonProperty("user")
    var twitterUser: String? = null
    @get:JsonProperty("userDate")
    var userCreationDate: Date? = null
    @get:JsonProperty("screenName")
    var userScreenName: String? = null
    @get:JsonProperty("text")
    var twitterText: String? = null
    @get:JsonProperty("twitterDate")
    var twitterCreationDate: Date? = null

    override fun toString(): String {
        return "TwitterResponseDto{" +
                "twitterUser='" + twitterUser + '\''.toString() +
                ", userCreationDate=" + userCreationDate +
                ", userScreenName='" + userScreenName + '\''.toString() +
                ", twitterText='" + twitterText + '\''.toString() +
                ", twitterCreationDate=" + twitterCreationDate +
                '}'.toString()
    }
}
