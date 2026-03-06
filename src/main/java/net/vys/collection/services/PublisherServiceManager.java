package net.vys.collection.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.vys.collection.entities.Publisher;
import net.vys.collection.repositories.PublisherRepository;
import java.util.List;

@Service
public class PublisherServiceManager implements PublisherService {
    @Autowired
    private PublisherRepository repository;

    @Override
    public List<Publisher> findAll() {
        return (List<Publisher>) this.repository.findAll();
    }

    @Override
    public Publisher findById(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Publisher save(Publisher publisher) {
        return this.repository.save(publisher);
    }
}
