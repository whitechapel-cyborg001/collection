package net.vys.collection.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import net.vys.collection.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    
}
