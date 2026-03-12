package net.vys.collection.services;

//import net.vys.collection.entities.Publisher;
import net.vys.collection.dto.PublisherDTO;
import net.vys.collection.dto.PublisherResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PublisherService {
    Page<PublisherResponseDTO> findAll(Pageable pageable);
    PublisherResponseDTO findById(Long id);
    PublisherResponseDTO save(PublisherDTO publisher);
    PublisherResponseDTO update(Long id, PublisherDTO publisher);
    void delete(Long id);
}
