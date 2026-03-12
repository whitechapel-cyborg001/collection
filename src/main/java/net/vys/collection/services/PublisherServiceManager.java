package net.vys.collection.services;

import org.springframework.stereotype.Service;

import net.vys.collection.entities.Publisher;
import net.vys.collection.exceptions.SerieNotFoundException;
import net.vys.collection.dto.PublisherDTO;
import net.vys.collection.dto.PublisherResponseDTO;
import net.vys.collection.repositories.PublisherRepository;
import net.vys.collection.mapper.PublisherMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class PublisherServiceManager implements PublisherService {

    private final PublisherRepository repository;
    private final PublisherMapper mapper;

    public PublisherServiceManager(PublisherRepository repository, PublisherMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Page<PublisherResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toPublisherResponseDTO);
    }

    @Override
    public PublisherResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(mapper::toPublisherResponseDTO)
                .orElseThrow(() -> new SerieNotFoundException(id));
    }

    @Override
    public PublisherResponseDTO save(PublisherDTO publisherDTO) {
        Publisher saved = repository.save(mapper.toPublisher(publisherDTO));
        return mapper.toPublisherResponseDTO(saved);
    }

    @Override
    public PublisherResponseDTO update(Long id, PublisherDTO publisherDTO) {
        Publisher existing = repository.findById(id)
                .orElseThrow(() -> new SerieNotFoundException(id));
        Publisher updated = mapper.toPublisher(publisherDTO);
        updated.setId(id);
        return mapper.toPublisherResponseDTO(repository.save(updated));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new SerieNotFoundException(id);
        }
        repository.deleteById(id);
    }
}