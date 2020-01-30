package org.interview.twitter.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Author")
public class Author {

    @Id
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Column(name = "AUTHOR_NAME")
    private String name;

    @Column(name = "SCREEN_NAME")
    private String screenName;

    @ManyToMany(mappedBy = "authors")
    private Set<TwitterMessage> twitterMessages;

    public Long getUserId() {
        return userId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Set<TwitterMessage> getTwitterMessages() {
        return twitterMessages;
    }

    public void setTwitterMessages(Set<TwitterMessage> twitterMessages) {
        this.twitterMessages = twitterMessages;
    }
}
