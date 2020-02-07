package org.interview.twitter.model

import com.fasterxml.jackson.annotation.JsonProperty

import java.util.Date

/**
 * This is a Dto class
 *
 * @Author Mehdi
 */
data class TwitterResponseDto(@JsonProperty("userID") var userID: Long?,
                              @JsonProperty("user") var twitterUser: String?,
                              @JsonProperty("userDate") var userCreationDate: Date?,
                              @JsonProperty("screenName") var userScreenName: String?,
                              @JsonProperty("twitterTextID") var twitterTextID: Long?,
                              @JsonProperty("text") var twitterText: String?,
                              @JsonProperty("twitterDate") var twitterCreationDate: Date?) {
    constructor() : this(null,null, null, null, null,null,null)
}
