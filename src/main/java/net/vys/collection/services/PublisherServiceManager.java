package net.vys.collection.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.vys.collection.entities.Publisher;
import net.vys.collection.dto.PublisherDTO;
import net.vys.collection.dto.PublisherResponseDTO;
import net.vys.collection.repositories.PublisherRepository;

import java.util.List;

@Service
public class PublisherServiceManager implements PublisherService {

    @Autowired
    private PublisherRepository repository;

    @Override
    public List<PublisherResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(p -> new PublisherResponseDTO(
                        p.getId(),
                        p.getName()
                ))
                .toList();
    }

    @Override
    public PublisherResponseDTO findById(Long id) {
        Publisher publisher = repository.findById(id).orElse(null);

        if (publisher == null) {
            return null;
        }

        return new PublisherResponseDTO(
                publisher.getId(),
                publisher.getName()
        );
    }

    @Override
    public PublisherResponseDTO save(PublisherDTO publisherDTO) {
        Publisher publisher = new Publisher();
        publisher.setName(publisherDTO.getName());

        Publisher saved = repository.save(publisher);

        return new PublisherResponseDTO(
                saved.getId(),
                saved.getName()
        );
    }
}