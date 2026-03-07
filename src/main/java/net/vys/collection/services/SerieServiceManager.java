package net.vys.collection.services;

import net.vys.collection.dto.SerieResponseDTO;
import net.vys.collection.dto.SerieDTO;
import net.vys.collection.entities.Serie;
import net.vys.collection.repositories.SerieRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SerieServiceManager implements SerieService {

    private final SerieRepository repository;
    public SerieServiceManager(SerieRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SerieResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(s -> new SerieResponseDTO(
                        s.getId(),
                        s.getName(),
                        s.getIssues()
                ))
                .toList();
    }

    @Override
    public SerieResponseDTO findById(Long id) {
        Serie serie = this.repository.findById(id).orElse(null);

        if (serie == null) {
            return null;
        }

        return new SerieResponseDTO(
                serie.getId(),
                serie.getName(),
                serie.getIssues()
        );
    }

    @Override
    public SerieResponseDTO save(SerieDTO serieDTO) {
        Serie serie = new Serie();
        serie.setName(serieDTO.getName());
        serie.setIssues(serieDTO.getIssues());

        Serie saved = this.repository.save(serie);

        return new SerieResponseDTO(
                saved.getId(),
                saved.getName(),
                saved.getIssues()
        );
    }
    
}
