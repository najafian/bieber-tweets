package org.interview.twitter.service.facade;

import org.interview.twitter.model.Author;
import org.interview.twitter.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Transactional
    public boolean saveAuthorEntity(Author author) {
        authorRepository.save(author);
        return true;
    }

    @Transactional
    public List<Author> findAllAuthorEntity(Author author) {
        return authorRepository.findAll();
    }
}
