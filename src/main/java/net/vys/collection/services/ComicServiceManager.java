package net.vys.collection.services;

import org.springframework.beans.factory.annotation.Autowired;
import net.vys.collection.repositories.ComicRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import net.vys.collection.entities.Comic;

@Service
public class ComicServiceManager implements ComicService {

    @Autowired
    private ComicRepository repository;

    @Override
    public List<Comic> findAll() {
        return (List<Comic>) this.repository.findAll();
    }

    @Override
    public Comic findById(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Comic save(Comic comic) {
        return this.repository.save(comic);
    }

    @Override
    public List<Comic> findByAuthor(Long authorId) {

    // TODO : findByAuthor
        return null;
    }


}
