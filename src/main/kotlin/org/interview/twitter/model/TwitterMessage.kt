package org.interview.twitter.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import javax.persistence.*
import java.io.Serializable
import java.util.Date
import kotlin.jvm.Transient

/**
 * @Author Mehdi
 */
@Entity
@Table(name = "MESSAGE")
class TwitterMessage {
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

    constructor(messageId: Long?, creationDate: Date?, messageText: String?, author: Author?) {
        this.messageId = messageId
        this.creationDate = creationDate
        this.messageText = messageText
        this.author = author
    }

    constructor()

    override fun toString(): String {
        return "TwitterMessage(messageId=$messageId, creationDate=$creationDate, messageText=$messageText, author=$author)"
    }
}
