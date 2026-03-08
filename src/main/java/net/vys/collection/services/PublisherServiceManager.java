package net.vys.collection.services;

import org.springframework.stereotype.Service;

import net.vys.collection.entities.Publisher;
import net.vys.collection.dto.PublisherDTO;
import net.vys.collection.dto.PublisherResponseDTO;
import net.vys.collection.repositories.PublisherRepository;
import net.vys.collection.mapper.PublisherMapper;

import java.util.List;

@Service
public class PublisherServiceManager implements PublisherService {

    private final PublisherRepository repository;
    private final PublisherMapper mapper;

    public PublisherServiceManager(PublisherRepository repository, PublisherMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<PublisherResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toPublisherResponseDTO)
                .toList();
    }

    @Override
    public PublisherResponseDTO findById(Long id) {
        Publisher publisher = repository.findById(id).orElse(null);

        if (publisher == null) {
            return null;
        }

        return mapper.toPublisherResponseDTO(publisher);
    }

    @Override
    public PublisherResponseDTO save(PublisherDTO publisherDTO) {
        Publisher publisher = new Publisher();
        publisher = mapper.toPublisher(publisherDTO);
        Publisher saved = repository.save(publisher);

        return mapper.toPublisherResponseDTO(saved);
    }
}