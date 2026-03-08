package net.vys.collection.mapper;

import org.mapstruct.Mapper;

import net.vys.collection.dto.PublisherDTO;
import net.vys.collection.entities.Publisher;
import net.vys.collection.dto.PublisherResponseDTO;

@Mapper(componentModel = "spring")
public interface PublisherMapper {
    PublisherResponseDTO toPublisherResponseDTO(Publisher publisher);
    PublisherDTO toPublisherDTO(Publisher publisher);
    Publisher toPublisher(PublisherDTO publisherDTO);

    // Conversión Publisher -> ID
    default Long map(Publisher publisher) {
        return publisher != null ? publisher.getId() : null;
    }

    // Conversión ID -> Publisher
    default Publisher map(Long id) {
        if (id == null) {
            return null;
        }
        Publisher publisher = new Publisher();
        publisher.setId(id);
        return publisher;
    }
}
