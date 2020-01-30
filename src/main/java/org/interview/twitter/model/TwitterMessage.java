package org.interview.twitter.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "MESSAGE")
public class TwitterMessage implements Comparable{

    @Id
    @Column(name = "MESSAGE_ID")
    private Long messageId;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Column(name = "MESSAGE_TEXT")
    private String messageText;

    @Column(name = "AUTHOR")
    @ManyToMany
    @JoinTable(name = "AUTHOR_MESSAGE",
            joinColumns = @JoinColumn(name = "MESSAGE_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID"))
    private Set<Author> authors;

    public Long getMessageId() {
        return messageId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getMessageText() {
        return messageText;
    }



    public int compareTo(Object o) {
        final int EQUAL = 0;
        int result;

        TwitterMessage oTwitterMessage = (TwitterMessage) o;

        if(this == oTwitterMessage)
            result = EQUAL;
        else
            result = this.getCreationDate().compareTo(oTwitterMessage.getCreationDate());

        return result;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
