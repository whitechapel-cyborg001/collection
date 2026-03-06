package net.vys.collection.services;
import java.util.List;
import net.vys.collection.entities.Comic;
import net.vys.collection.dto.ComicDTO;

public interface ComicService {
    List<Comic> findAll();
    Comic findById(Long id);
    Comic save(ComicDTO comic);
    List<Comic> findByAuthor(Long authorId);
}
