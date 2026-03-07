package net.vys.collection.services;
import java.util.List;
import net.vys.collection.dto.ComicResponseDTO;
import net.vys.collection.dto.ComicDTO;

public interface ComicService {
    List<ComicResponseDTO> findAll();
    ComicResponseDTO findById(Long id);
    ComicResponseDTO save(ComicDTO comicDTO);
}
