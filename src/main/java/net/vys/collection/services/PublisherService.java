package net.vys.collection.services;

//import net.vys.collection.entities.Publisher;
import net.vys.collection.dto.PublisherDTO;
import net.vys.collection.dto.PublisherResponseDTO;
import java.util.List;

public interface PublisherService {
    List<PublisherResponseDTO> findAll();
    PublisherResponseDTO findById(Long id);
    PublisherResponseDTO save(PublisherDTO publisher);
}
