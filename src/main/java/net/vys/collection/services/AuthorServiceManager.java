package net.vys.collection.services;

//import net.vys.collection.entities.Author;
import net.vys.collection.dto.AuthorDTO;
import net.vys.collection.dto.AuthorResponseDTO;
import net.vys.collection.entities.Author;
import net.vys.collection.repositories.AuthorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthorServiceManager implements AuthorService {

    private final AuthorRepository repository;
    public AuthorServiceManager(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AuthorResponseDTO> findAll() {
        return repository.findAll()
            .stream()
            .map(p -> new AuthorResponseDTO(
                    p.getId(),
                    p.getName()
            ))
            .toList();
    }

    @Override
    public AuthorResponseDTO findById(Long id) {
        return repository.findById(id)
            .map(p -> new AuthorResponseDTO(
                    p.getId(),
                    p.getName()
            ))
            .orElse(null);
    }

    @Override
    public AuthorResponseDTO save(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setName(authorDTO.getName());

        Author saved = repository.save(author);

        return new AuthorResponseDTO(
            saved.getId(),
            saved.getName()
        );
    }
}
