package net.vys.collection.services;

import net.vys.collection.dto.SerieResponseDTO;
import net.vys.collection.dto.SerieDTO;
import net.vys.collection.entities.Serie;
import net.vys.collection.exceptions.SerieNotFoundException;
import net.vys.collection.repositories.SerieRepository;
import net.vys.collection.mapper.SerieMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    public Page<SerieResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toSerieResponseDTO);
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
    
    @Override
    public SerieResponseDTO update(Long id, SerieDTO serieDTO) {
        Serie existing = repository.findById(id)
                .orElseThrow(() -> new SerieNotFoundException(id));
        Serie updated = mapper.toSerie(serieDTO);
        updated.setId(id);
        return mapper.toSerieResponseDTO(repository.save(updated));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new SerieNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
