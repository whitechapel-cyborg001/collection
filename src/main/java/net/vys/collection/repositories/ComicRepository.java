package net.vys.collection.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import net.vys.collection.entities.Comic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ComicRepository extends JpaRepository<Comic, Long>, JpaSpecificationExecutor<Comic> {
    Page<Comic> findAll(Pageable pageable);
}
