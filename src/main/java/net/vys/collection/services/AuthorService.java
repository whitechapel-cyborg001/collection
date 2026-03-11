package net.vys.collection.services;

//import net.vys.collection.entities.Author;
import net.vys.collection.dto.AuthorDTO;
import net.vys.collection.dto.AuthorResponseDTO;
import java.util.List;

public interface AuthorService {
    List<AuthorResponseDTO> findAll();
    AuthorResponseDTO findById(Long id);
    AuthorResponseDTO save(AuthorDTO authorDTO);
    AuthorResponseDTO update(Long id, AuthorDTO authorDTO);
    void delete(Long id);
}
