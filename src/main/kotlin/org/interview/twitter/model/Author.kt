package org.interview.twitter.model

import javax.persistence.*
import java.util.Date

/**
 * @authors Mehdi Najafian
 */
@Entity
@Table(name = "Author")
class Author : Comparable<Author> {
    //this constructor is for Jpa
    constructor()

    constructor(userId: Long?, creationDate: Date?, name: String?, screenName: String?, twitterMessage: List<TwitterMessage>?) {
        this.userId = userId
        this.creationDate = creationDate
        this.name = name
        this.screenName = screenName
        this.twitterMessage = twitterMessage
    }

    @Id
    @Column(name = "USER_ID",unique = true)
    var userId: Long? = null

    @Column(name = "CREATION_DATE")
    var creationDate: Date? = null

    @Column(name = "AUTHOR_NAME")
    var name: String? = null

    @Column(name = "SCREEN_NAME")
    var screenName: String? = null

    @OneToMany(mappedBy = "author")
    var twitterMessage: List<TwitterMessage>? = null

    override fun compareTo(other: Author): Int {
        var result = 0
        if (other !== this)
            result = this.creationDate!!.compareTo(other.creationDate)
        return result
    }

}
