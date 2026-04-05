package net.vys.collection.services;

import net.vys.collection.dto.ComicResponseDTO;
import net.vys.collection.dto.UserComicResponseDTO;
import net.vys.collection.entities.Comic;
import net.vys.collection.entities.User;
import net.vys.collection.entities.UserComic;
import net.vys.collection.entities.UserComic.CollectionStatus;
import net.vys.collection.exceptions.ComicAlreadyInCollectionException;
import net.vys.collection.exceptions.ComicNotFoundException;
import net.vys.collection.exceptions.UserNotFoundException;
import net.vys.collection.mapper.ComicMapper;
import net.vys.collection.repositories.ComicRepository;
import net.vys.collection.repositories.UserComicRepository;
import net.vys.collection.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserComicServiceManagerTest {

    @Mock private UserComicRepository userComicRepository;
    @Mock private ComicRepository comicRepository;
    @Mock private UserRepository userRepository;
    @Mock private ComicMapper comicMapper;

    @InjectMocks
    private UserComicServiceManager userComicService;

    private User user;
    private Comic comic;
    private UserComic userComic;
    private UserComic userComicWanted;
    private ComicResponseDTO comicResponseDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("vyserad");
        user.setPassword("hashed");
        user.setRole("USER");

        comic = new Comic();
        comic.setId(1L);
        comic.setTitle("Batman #1");

        userComic = new UserComic();
        userComic.setUser(user);
        userComic.setComic(comic);
        userComic.setStatus(CollectionStatus.OWNED);

        userComicWanted = new UserComic();
        userComicWanted.setUser(user);
        userComicWanted.setComic(comic);
        userComicWanted.setStatus(CollectionStatus.WANTED);

        comicResponseDTO = new ComicResponseDTO(1L, "Batman #1", null, null, null, null, null, null);
    }

    // =====================
    // addToCollection
    // =====================

    @Test
    void addToCollection_shouldReturnOwnedDTO_whenStatusIsOwned() {
        when(userRepository.findByUsername("vyserad")).thenReturn(Optional.of(user));
        when(comicRepository.findById(1L)).thenReturn(Optional.of(comic));
        when(userComicRepository.existsByUserAndComicId(user, 1L)).thenReturn(false);
        when(userComicRepository.save(any(UserComic.class))).thenReturn(userComic);
        when(comicMapper.toComicResponseDTO(comic)).thenReturn(comicResponseDTO);

        UserComicResponseDTO result = userComicService.addToCollection(1L, "vyserad", CollectionStatus.OWNED);

        assertNotNull(result);
        assertEquals("Batman #1", result.getComic().getTitle());
        assertEquals(CollectionStatus.OWNED, result.getStatus());
        verify(userComicRepository, times(1)).save(any(UserComic.class));
    }

    @Test
    void addToCollection_shouldReturnWantedDTO_whenStatusIsWanted() {
        when(userRepository.findByUsername("vyserad")).thenReturn(Optional.of(user));
        when(comicRepository.findById(1L)).thenReturn(Optional.of(comic));
        when(userComicRepository.existsByUserAndComicId(user, 1L)).thenReturn(false);
        when(userComicRepository.save(any(UserComic.class))).thenReturn(userComicWanted);
        when(comicMapper.toComicResponseDTO(comic)).thenReturn(comicResponseDTO);

        UserComicResponseDTO result = userComicService.addToCollection(1L, "vyserad", CollectionStatus.WANTED);

        assertNotNull(result);
        assertEquals(CollectionStatus.WANTED, result.getStatus());
        verify(userComicRepository, times(1)).save(any(UserComic.class));
    }

    @Test
    void addToCollection_shouldThrowUserNotFoundException_whenUserNotFound() {
        when(userRepository.findByUsername("noexiste")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userComicService.addToCollection(1L, "noexiste", CollectionStatus.OWNED));
        verify(userComicRepository, never()).save(any());
    }

    @Test
    void addToCollection_shouldThrowComicNotFoundException_whenComicNotFound() {
        when(userRepository.findByUsername("vyserad")).thenReturn(Optional.of(user));
        when(comicRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ComicNotFoundException.class,
                () -> userComicService.addToCollection(99L, "vyserad", CollectionStatus.OWNED));
        verify(userComicRepository, never()).save(any());
    }

    @Test
    void addToCollection_shouldThrowComicAlreadyInCollectionException_whenAlreadyInCollection() {
        when(userRepository.findByUsername("vyserad")).thenReturn(Optional.of(user));
        when(comicRepository.findById(1L)).thenReturn(Optional.of(comic));
        when(userComicRepository.existsByUserAndComicId(user, 1L)).thenReturn(true);

        assertThrows(ComicAlreadyInCollectionException.class,
                () -> userComicService.addToCollection(1L, "vyserad", CollectionStatus.OWNED));
        verify(userComicRepository, never()).save(any());
    }

    // =====================
    // removeFromCollection
    // =====================

    @Test
    void removeFromCollection_shouldDeleteUserComic_whenValid() {
        when(userRepository.findByUsername("vyserad")).thenReturn(Optional.of(user));
        when(userComicRepository.findByUserAndComicId(user, 1L)).thenReturn(Optional.of(userComic));

        userComicService.removeFromCollection(1L, "vyserad");

        verify(userComicRepository, times(1)).delete(userComic);
    }

    @Test
    void removeFromCollection_shouldThrowUserNotFoundException_whenUserNotFound() {
        when(userRepository.findByUsername("noexiste")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userComicService.removeFromCollection(1L, "noexiste"));
        verify(userComicRepository, never()).delete(any());
    }

    @Test
    void removeFromCollection_shouldThrowComicNotFoundException_whenComicNotInCollection() {
        when(userRepository.findByUsername("vyserad")).thenReturn(Optional.of(user));
        when(userComicRepository.findByUserAndComicId(user, 1L)).thenReturn(Optional.empty());

        assertThrows(ComicNotFoundException.class,
                () -> userComicService.removeFromCollection(1L, "vyserad"));
        verify(userComicRepository, never()).delete(any());
    }

    // =====================
    // getCollection
    // =====================

    @Test
    void getCollection_shouldReturnListOfUserComicResponseDTOs_whenValid() {
        when(userRepository.findByUsername("vyserad")).thenReturn(Optional.of(user));
        when(userComicRepository.findByUser(user)).thenReturn(List.of(userComic));
        when(comicMapper.toComicResponseDTO(comic)).thenReturn(comicResponseDTO);

        List<UserComicResponseDTO> result = userComicService.getCollection("vyserad");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Batman #1", result.get(0).getComic().getTitle());
    }

    @Test
    void getCollection_shouldReturnEmptyList_whenNoComics() {
        when(userRepository.findByUsername("vyserad")).thenReturn(Optional.of(user));
        when(userComicRepository.findByUser(user)).thenReturn(List.of());

        List<UserComicResponseDTO> result = userComicService.getCollection("vyserad");

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void getCollection_shouldThrowUserNotFoundException_whenUserNotFound() {
        when(userRepository.findByUsername("noexiste")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userComicService.getCollection("noexiste"));
        verify(userComicRepository, never()).findByUser(any());
    }
}