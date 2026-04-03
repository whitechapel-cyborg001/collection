package net.vys.collection.services;

import net.vys.collection.dto.AuthorDTO;
import net.vys.collection.dto.AuthorResponseDTO;
import net.vys.collection.entities.Author;
import net.vys.collection.exceptions.AuthorNotFoundException;
import net.vys.collection.mapper.AuthorMapper;
import net.vys.collection.repositories.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceManagerTest {

    @Mock private AuthorRepository repository;
    @Mock private AuthorMapper mapper;

    @InjectMocks
    private AuthorServiceManager authorService;

    private Author author;
    private AuthorDTO authorDTO;
    private AuthorResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setId(1L);
        author.setName("Frank Miller");

        authorDTO = new AuthorDTO();
        authorDTO.setName("Frank Miller");

        responseDTO = new AuthorResponseDTO(1L, "Frank Miller");
    }

    // =====================
    // findAll
    // =====================

    @Test
    void findAll_shouldReturnPageOfAuthors() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Author> authorPage = new PageImpl<>(List.of(author));

        when(repository.findAll(pageable)).thenReturn(authorPage);
        when(mapper.toAuthorResponseDTO(author)).thenReturn(responseDTO);

        Page<AuthorResponseDTO> result = authorService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Frank Miller", result.getContent().get(0).getName());
    }

    @Test
    void findAll_shouldReturnEmptyPage_whenNoAuthors() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Author> emptyPage = new PageImpl<>(List.of());

        when(repository.findAll(pageable)).thenReturn(emptyPage);

        Page<AuthorResponseDTO> result = authorService.findAll(pageable);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
    }

    // =====================
    // findById
    // =====================

    @Test
    void findById_shouldReturnAuthor_whenExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(author));
        when(mapper.toAuthorResponseDTO(author)).thenReturn(responseDTO);

        AuthorResponseDTO result = authorService.findById(1L);

        assertNotNull(result);
        assertEquals("Frank Miller", result.getName());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void findById_shouldThrowAuthorNotFoundException_whenNotExists() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> authorService.findById(99L));
        verify(repository, times(1)).findById(99L);
    }

    // =====================
    // save
    // =====================

    @Test
    void save_shouldReturnSavedAuthor() {
        when(mapper.toAuthor(authorDTO)).thenReturn(author);
        when(repository.save(author)).thenReturn(author);
        when(mapper.toAuthorResponseDTO(author)).thenReturn(responseDTO);

        AuthorResponseDTO result = authorService.save(authorDTO);

        assertNotNull(result);
        assertEquals("Frank Miller", result.getName());
        verify(repository, times(1)).save(author);
    }

    // =====================
    // update
    // =====================

    @Test
    void update_shouldReturnUpdatedAuthor_whenExists() {
        Author updated = new Author();
        updated.setName("Frank Miller Updated");

        AuthorDTO updatedDTO = new AuthorDTO();
        updatedDTO.setName("Frank Miller Updated");

        AuthorResponseDTO updatedResponse = new AuthorResponseDTO(1L, "Frank Miller Updated");

        when(repository.findById(1L)).thenReturn(Optional.of(author));
        when(mapper.toAuthor(updatedDTO)).thenReturn(updated);
        when(repository.save(updated)).thenReturn(updated);
        when(mapper.toAuthorResponseDTO(updated)).thenReturn(updatedResponse);

        AuthorResponseDTO result = authorService.update(1L, updatedDTO);

        assertNotNull(result);
        assertEquals("Frank Miller Updated", result.getName());
        assertEquals(1L, updated.getId()); // verifica que se le asignó el id
        verify(repository, times(1)).save(updated);
    }

    @Test
    void update_shouldThrowAuthorNotFoundException_whenNotExists() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> authorService.update(99L, authorDTO));
        verify(repository, never()).save(any());
    }

    // =====================
    // delete
    // =====================

    @Test
    void delete_shouldCallDeleteById_whenAuthorExists() {
        when(repository.existsById(1L)).thenReturn(true);

        authorService.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void delete_shouldThrowAuthorNotFoundException_whenNotExists() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThrows(AuthorNotFoundException.class, () -> authorService.delete(99L));
        verify(repository, never()).deleteById(any());
    }
}