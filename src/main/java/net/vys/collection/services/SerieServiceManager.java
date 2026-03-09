package net.vys.collection.services;

import net.vys.collection.dto.SerieResponseDTO;
import net.vys.collection.dto.SerieDTO;
import net.vys.collection.entities.Serie;
import net.vys.collection.exceptions.SerieNotFoundException;
import net.vys.collection.repositories.SerieRepository;
import net.vys.collection.mapper.SerieMapper;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SerieServiceManager implements SerieService {

    private final SerieRepository repository;
    private final SerieMapper mapper;
    public SerieServiceManager(SerieRepository repository, SerieMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<SerieResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toSerieResponseDTO)
                .toList();
    }

    @Override
    public SerieResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(mapper::toSerieResponseDTO)
                .orElseThrow(() -> new SerieNotFoundException(id));
    }

    @Override
    public SerieResponseDTO save(SerieDTO serieDTO) {
        Serie saved = repository.save(mapper.toSerie(serieDTO));
        return mapper.toSerieResponseDTO(saved);
    }
    
}
