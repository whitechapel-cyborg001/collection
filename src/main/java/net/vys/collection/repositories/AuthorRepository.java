package net.vys.collection.repositories;
import org.springframework.data.repository.CrudRepository;
import net.vys.collection.entities.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    
}
