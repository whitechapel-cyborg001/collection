package net.vys.collection.mapper;

import org.mapstruct.Mapper;

import net.vys.collection.dto.AuthorResponseDTO;
import net.vys.collection.entities.Author;
import net.vys.collection.dto.AuthorDTO;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorResponseDTO toAuthorResponseDTO(Author author);
    AuthorDTO toAuthorDTO(Author author);
    Author toAuthorFromDTO(AuthorDTO authorDTO);

    // Conversión Author -> ID
    default Long map(Author author) {
        return author != null ? author.getId() : null;
    }

    // Conversión ID -> Author
    default Author map(Long id) {
        if (id == null) {
            return null;
        }
        Author author = new Author();
        author.setId(id);
        return author;
    }
}
