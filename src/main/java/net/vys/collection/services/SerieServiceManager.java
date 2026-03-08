package net.vys.collection.services;

import net.vys.collection.dto.SerieResponseDTO;
import net.vys.collection.dto.SerieDTO;
import net.vys.collection.entities.Serie;
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
        Serie serie = this.repository.findById(id).orElse(null);

        if (serie == null) {
            return null;
        }

        return mapper.toSerieResponseDTO(serie);
    }

    @Override
    public SerieResponseDTO save(SerieDTO serieDTO) {
        Serie serie = new Serie();
        serie = mapper.toSerie(serieDTO);

        Serie saved = this.repository.save(serie);

        return mapper.toSerieResponseDTO(saved);
    }
    
}
