package org.interview.twitter.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import java.util.Date

/**
 * @authors Mehdi Najafian
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class AuthorMapper(@JsonProperty("id") val userId: String?,
                        @JsonProperty("created_at") val creationDate: Date?,
                        @JsonProperty("name") val name: String?,
                        @JsonProperty("screen_name") val screenName: String?)
