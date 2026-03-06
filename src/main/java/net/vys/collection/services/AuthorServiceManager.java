package net.vys.collection.services;

import org.springframework.beans.factory.annotation.Autowired;

import net.vys.collection.entities.Author;
import net.vys.collection.repositories.AuthorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthorServiceManager implements AuthorService {
    @Autowired
    private AuthorRepository repository;

    @Override
    public List<Author> findAll() {
        return (List<Author>) this.repository.findAll();
    }

    @Override
    public Author findById(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Author save(Author author) {
        return this.repository.save(author);
    }
}
