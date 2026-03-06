package net.vys.collection.services;

import net.vys.collection.entities.Publisher;
import java.util.List;

public interface PublisherService {
    List<Publisher> findAll();
    Publisher findById(Long id);
    Publisher save(Publisher publisher);
}
