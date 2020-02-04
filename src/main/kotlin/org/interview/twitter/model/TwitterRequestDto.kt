package org.interview.twitter.model


import com.fasterxml.jackson.annotation.JsonProperty

import java.io.Serializable

/**
 * This is a Dto class
 *
 * @Author Mehdi
 */
data class TwitterRequestDto(@JsonProperty("applicationName") val applicationName: String?,
                             @JsonProperty("pinID") var pinID: String?,
                             @JsonProperty("consumerKey") var consumerKey: String?,
                             @JsonProperty("consumerSecurityKey") val consumerSecurityKey: String?,
                             @JsonProperty("keywordSearch") val keywordSearch: String?
                             ) :Serializable
