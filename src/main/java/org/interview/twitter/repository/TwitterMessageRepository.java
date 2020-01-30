package org.interview.twitter.repository;

import org.interview.twitter.model.TwitterMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwitterMessageRepository extends JpaRepository<TwitterMessage, Long> {
}
