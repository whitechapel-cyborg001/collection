package net.vys.collection.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import net.vys.collection.entities.Comic;

public interface ComicRepository extends JpaRepository<Comic, Long> {
    
}
