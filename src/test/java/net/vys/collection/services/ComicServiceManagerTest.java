package net.vys.collection.services;

import net.vys.collection.dto.ComicDTO;
import net.vys.collection.dto.ComicResponseDTO;
import net.vys.collection.entities.Author;
import net.vys.collection.entities.Comic;
import net.vys.collection.entities.Publisher;
import net.vys.collection.entities.Serie;
import net.vys.collection.exceptions.AuthorNotFoundException;
import net.vys.collection.exceptions.ComicNotFoundException;
import net.vys.collection.exceptions.PublisherNotFoundException;
import net.vys.collection.exceptions.SerieNotFoundException;
import net.vys.collection.mapper.ComicMapper;
import net.vys.collection.repositories.AuthorRepository;
import net.vys.collection.repositories.ComicRepository;
import net.vys.collection.repositories.PublisherRepository;
import net.vys.collection.repositories.SerieRepository;
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
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class ComicServiceManagerTest {

    @Mock private ComicRepository comicRepository;
    @Mock private AuthorRepository authorRepository;
    @Mock private PublisherRepository publisherRepository;
    @Mock private SerieRepository serieRepository;
    @Mock private ComicMapper mapper;

    @InjectMocks
    private ComicServiceManager comicService;

    private Comic comic;
    private ComicResponseDTO responseDTO;
    private ComicDTO comicDTO;
    private Author author;
    private Publisher publisher;
    private Serie serie;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setId(1L);
        author.setName("Frank Miller");

        publisher = new Publisher();
        publisher.setId(1L);
        publisher.setName("DC Comics");

        serie = new Serie();
        serie.setId(1L);
        serie.setName("Batman");
        serie.setIssues(2);

        comic = new Comic();
        comic.setId(1L);
        comic.setTitle("Batman #1");
        comic.setAuthors(List.of(author));
        comic.setPublisher(publisher);
        comic.setSerie(serie);
        comic.setIssue(1);
        comic.setNotes("First issue of the series");

        responseDTO = new ComicResponseDTO(1L, "Batman #1", null, null, null, null, null, null);

        comicDTO = new ComicDTO();
        comicDTO.setTitle("Batman #1");
        comicDTO.setPublisher(1L);
        comicDTO.setSerie(1L);
        comicDTO.setAuthors(List.of(1L));
        comicDTO.setIssue(1);
        comicDTO.setNotes("First issue of the series");

    }

    // =====================
    // findAll
    // =====================

    @Test
    void findAll_shouldReturnPageOfComics() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Comic> comicPage = new PageImpl<>(List.of(comic));

        when(comicRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(comicPage);
        when(mapper.toComicResponseDTO(comic)).thenReturn(responseDTO);

        Page<ComicResponseDTO> result = comicService.findAll(null, null, null, null, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Batman #1", result.getContent().get(0).getTitle());
    }

    @Test
    void findAll_shouldReturnEmptyPage_whenNoResults() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Comic> emptyPage = new PageImpl<>(List.of());

        when(comicRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(emptyPage);

        Page<ComicResponseDTO> result = comicService.findAll("Inexistente", null, null, null, pageable);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
    }

    // =====================
    // findById
    // =====================

    @Test
    void findById_shouldReturnComic_whenExists() {
        when(comicRepository.findById(1L)).thenReturn(Optional.of(comic));
        when(mapper.toComicResponseDTO(comic)).thenReturn(responseDTO);

        ComicResponseDTO result = comicService.findById(1L);

        assertNotNull(result);
        assertEquals("Batman #1", result.getTitle());
        verify(comicRepository, times(1)).findById(1L);
    }

    @Test
    void findById_shouldThrowComicNotFoundException_whenNotExists() {
        when(comicRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ComicNotFoundException.class, () -> comicService.findById(99L));
        verify(comicRepository, times(1)).findById(99L);
    }

    // =====================
    // save
    // =====================

    @Test
    void save_shouldReturnSavedComic_whenAllRelationsExist() {
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));
        when(serieRepository.findById(1L)).thenReturn(Optional.of(serie));
        when(authorRepository.findAllById(List.of(1L))).thenReturn(List.of(author));
        when(mapper.toComic(comicDTO)).thenReturn(comic);
        when(comicRepository.save(comic)).thenReturn(comic);
        when(mapper.toComicResponseDTO(comic)).thenReturn(responseDTO);

        ComicResponseDTO result = comicService.save(comicDTO);

        assertNotNull(result);
        assertEquals("Batman #1", result.getTitle());
        verify(comicRepository, times(1)).save(comic);
    }

    @Test
    void save_shouldThrowPublisherNotFoundException_whenPublisherNotExists() {
        when(publisherRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PublisherNotFoundException.class, () -> comicService.save(comicDTO));
        verify(comicRepository, never()).save(any());
    }

    @Test
    void save_shouldThrowSerieNotFoundException_whenSerieNotExists() {
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));
        when(serieRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SerieNotFoundException.class, () -> comicService.save(comicDTO));
        verify(comicRepository, never()).save(any());
    }

    @Test
    void save_shouldThrowAuthorNotFoundException_whenAuthorNotExists() {
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));
        when(serieRepository.findById(1L)).thenReturn(Optional.of(serie));
        // El repository devuelve lista vacía — el author no existe
        when(authorRepository.findAllById(List.of(1L))).thenReturn(List.of());

        assertThrows(AuthorNotFoundException.class, () -> comicService.save(comicDTO));
        verify(comicRepository, never()).save(any());
    }

    // =====================
    // update
    // =====================

    @Test
    void update_shouldReturnUpdatedComic_whenAllRelationsExist() {
        when(comicRepository.findById(1L)).thenReturn(Optional.of(comic));
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));
        when(serieRepository.findById(1L)).thenReturn(Optional.of(serie));
        when(authorRepository.findAllById(List.of(1L))).thenReturn(List.of(author));
        when(comicRepository.save(comic)).thenReturn(comic);
        when(mapper.toComicResponseDTO(comic)).thenReturn(responseDTO);

        ComicResponseDTO result = comicService.update(1L, comicDTO);

        assertNotNull(result);
        verify(comicRepository, times(1)).save(comic);
    }

    @Test
    void update_shouldThrowComicNotFoundException_whenComicNotExists() {
        when(comicRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ComicNotFoundException.class, () -> comicService.update(99L, comicDTO));
        verify(comicRepository, never()).save(any());
    }

    @Test
    void update_shouldThrowPublisherNotFoundException_whenPublisherNotExists() {
        when(comicRepository.findById(1L)).thenReturn(Optional.of(comic));
        when(publisherRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PublisherNotFoundException.class, () -> comicService.update(1L, comicDTO));
        verify(comicRepository, never()).save(any());
    }

    // =====================
    // delete
    // =====================

    @Test
    void delete_shouldCallDeleteById_whenComicExists() {
        when(comicRepository.existsById(1L)).thenReturn(true);

        comicService.delete(1L);

        verify(comicRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_shouldThrowComicNotFoundException_whenNotExists() {
        when(comicRepository.existsById(99L)).thenReturn(false);

        assertThrows(ComicNotFoundException.class, () -> comicService.delete(99L));
        verify(comicRepository, never()).deleteById(any());
    }
}