package net.vys.collection.services;
import java.util.List;
import net.vys.collection.entities.Comic;

public interface ComicService {
    List<Comic> findAll();
    Comic findById(Long id);
    Comic save(Comic comic);
    List<Comic> findByAuthor(Long authorId);
}
