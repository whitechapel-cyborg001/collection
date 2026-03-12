package net.vys.collection.services;

import net.vys.collection.dto.SerieDTO;
import net.vys.collection.dto.SerieResponseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SerieService {
    Page<SerieResponseDTO> findAll(Pageable pageable);
    SerieResponseDTO findById(Long id);
    SerieResponseDTO save(SerieDTO serie);
    SerieResponseDTO update(Long id, SerieDTO serie);
    void delete(Long id);
}
