package net.vys.collection.services;

//import net.vys.collection.entities.Author;
import net.vys.collection.dto.AuthorDTO;
import net.vys.collection.dto.AuthorResponseDTO;
import net.vys.collection.entities.Author;
import net.vys.collection.repositories.AuthorRepository;

import org.springframework.stereotype.Service;
//import java.util.List;
import net.vys.collection.exceptions.AuthorNotFoundException;

import net.vys.collection.mapper.AuthorMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
public class AuthorServiceManager implements AuthorService {

    private final AuthorRepository repository;
    private final AuthorMapper mapper;

    public AuthorServiceManager(AuthorRepository repository, AuthorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Page<AuthorResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
            .map(mapper::toAuthorResponseDTO);
    }

    @Override
    public AuthorResponseDTO findById(Long id) {
        return repository.findById(id)
            .map(mapper::toAuthorResponseDTO)
            .orElseThrow(() -> new AuthorNotFoundException(id));
    }

    @Override
    public AuthorResponseDTO save(AuthorDTO authorDTO) {
        Author saved = repository.save(mapper.toAuthor(authorDTO));
        return mapper.toAuthorResponseDTO(saved);
    }

    @Override
    public AuthorResponseDTO update(Long id, AuthorDTO authorDTO) {
        Author existing = repository.findById(id)
            .orElseThrow(() -> new AuthorNotFoundException(id));
        Author updated = mapper.toAuthor(authorDTO);
        updated.setId(id);
        return mapper.toAuthorResponseDTO(repository.save(updated));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new AuthorNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
