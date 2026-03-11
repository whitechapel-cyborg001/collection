package net.vys.collection.services;

import net.vys.collection.dto.SerieDTO;
import net.vys.collection.dto.SerieResponseDTO;

import java.util.List;

public interface SerieService {
    List<SerieResponseDTO> findAll();
    SerieResponseDTO findById(Long id);
    SerieResponseDTO save(SerieDTO serie);
    SerieResponseDTO update(Long id, SerieDTO serie);
    void delete(Long id);
}
