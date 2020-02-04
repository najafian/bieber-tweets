package org.interview.twitter.repository

import org.interview.twitter.model.TwitterMessage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
/**
 * @authors Mehdi Najafian
 */
interface TwitterMessageRepository : JpaRepository<TwitterMessage?, Long?>{
    @Query("select t from TwitterMessage t order by t.author,t.creationDate")
    fun findAllSortedTweets(): List<TwitterMessage?>?
}