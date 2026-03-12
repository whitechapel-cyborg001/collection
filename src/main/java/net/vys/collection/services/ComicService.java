package net.vys.collection.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.vys.collection.dto.ComicResponseDTO;
import net.vys.collection.dto.ComicDTO;

public interface ComicService {
    //Page<ComicResponseDTO> findAll(Pageable pageable);
    ComicResponseDTO findById(Long id);
    ComicResponseDTO save(ComicDTO comicDTO);
    ComicResponseDTO update(Long id, ComicDTO comicDTO);
    void delete(Long id);
    Page<ComicResponseDTO> findAll(String title, Long authorId, Long serieId, Long publisherId, Pageable pageable);
}
