package org.interview.twitter.model

import javax.persistence.*
import java.util.Date

/**
 * @Author Mehdi
 */
@Entity
@Table(name = "Author")
class Author {
    @Id
    @Column(name = "USER_ID")
    var userId: Long? = null

    @Column(name = "CREATION_DATE")
    var creationDate: Date? = null

    @Column(name = "AUTHOR_NAME")
    var name: String? = null

    @Column(name = "SCREEN_NAME")
    var screenName: String? = null

    @OneToOne(mappedBy = "author")
    var twitterMessage: TwitterMessage? = null

    override fun toString(): String {
        return "Author(userId=$userId, creationDate=$creationDate, name=$name, screenName=$screenName, twitterMessage=$twitterMessage)"
    }

}
