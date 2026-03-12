package net.vys.collection.services;

//import net.vys.collection.entities.Author;
import net.vys.collection.dto.AuthorDTO;
import net.vys.collection.dto.AuthorResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {
    Page<AuthorResponseDTO> findAll(Pageable pageable);
    AuthorResponseDTO findById(Long id);
    AuthorResponseDTO save(AuthorDTO authorDTO);
    AuthorResponseDTO update(Long id, AuthorDTO authorDTO);
    void delete(Long id);
}
