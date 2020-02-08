package org.interview.twitter.model

import java.util.*
import javax.persistence.*

/**
 * @authors Mehdi Najafian
 */
@Entity
@Table(name = "MESSAGE")
class TwitterMessage : Comparable<TwitterMessage> {
    @Id
    @Column(name = "MESSAGE_ID")
    var messageId: Long? = null
    @Column(name = "CREATION_DATE")
    var creationDate: Date? = null
    @Column(name = "MESSAGE_TEXT")
    var messageText: String? = null
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", unique = false)
    var author: Author? = null

    //this constructor is for Jpa
    constructor()

    constructor(messageId: Long?, creationDate: Date?, messageText: String?, author: Author?) {
        this.messageId = messageId
        this.creationDate = creationDate
        this.messageText = messageText
        this.author = author
    }

    override fun compareTo(other: TwitterMessage): Int {
        var result = 0;
        if (other !== this)
            return this.creationDate!!.compareTo(other.creationDate)
        return result;
    }
}
