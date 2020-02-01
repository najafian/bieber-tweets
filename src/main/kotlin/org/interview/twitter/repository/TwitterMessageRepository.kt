package org.interview.twitter.repository

import org.interview.twitter.model.TwitterMessage
import org.springframework.data.jpa.repository.JpaRepository

interface TwitterMessageRepository : JpaRepository<TwitterMessage?, Long?>