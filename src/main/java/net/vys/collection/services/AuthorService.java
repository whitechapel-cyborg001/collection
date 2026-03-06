package net.vys.collection.services;

import net.vys.collection.entities.Author;
import java.util.List;

public interface AuthorService {
    List<Author> findAll();
    Author findById(Long id);
    Author save(Author author);
}
