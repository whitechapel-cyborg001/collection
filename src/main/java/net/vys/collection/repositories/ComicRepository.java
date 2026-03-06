package net.vys.collection.repositories;

import org.springframework.data.repository.CrudRepository;
import net.vys.collection.entities.Comic;

public interface ComicRepository extends CrudRepository<Comic, Long> {
    
}
