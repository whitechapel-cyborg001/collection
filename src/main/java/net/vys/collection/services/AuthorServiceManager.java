package net.vys.collection.services;

//import net.vys.collection.entities.Author;
import net.vys.collection.dto.AuthorDTO;
import net.vys.collection.dto.AuthorResponseDTO;
import net.vys.collection.entities.Author;
import net.vys.collection.repositories.AuthorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

import net.vys.collection.mapper.AuthorMapper;


@Service
public class AuthorServiceManager implements AuthorService {

    private final AuthorRepository repository;
    private final AuthorMapper mapper;

    public AuthorServiceManager(AuthorRepository repository, AuthorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<AuthorResponseDTO> findAll() {
        return repository.findAll()
            .stream()
            .map(mapper::toAuthorResponseDTO)
            .toList();
    }

    @Override
    public AuthorResponseDTO findById(Long id) {
        return repository.findById(id)
            .map(mapper::toAuthorResponseDTO)
            .orElse(null);
    }

    @Override
    public AuthorResponseDTO save(AuthorDTO authorDTO) {
        Author author = mapper.toAuthorFromDTO(authorDTO);
        Author saved = repository.save(author);

        return new AuthorResponseDTO(
            saved.getId(),
            saved.getName()
        );
    }
}
