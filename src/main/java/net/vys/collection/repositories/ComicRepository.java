package net.vys.collection.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import net.vys.collection.entities.Comic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ComicRepository extends JpaRepository<Comic, Long> {
    Page<Comic> findAll(Pageable pageable);
}
