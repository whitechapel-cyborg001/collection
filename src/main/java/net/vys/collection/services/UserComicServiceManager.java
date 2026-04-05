package net.vys.collection.services;
 
import net.vys.collection.dto.UserComicResponseDTO;
import net.vys.collection.entities.Comic;
import net.vys.collection.entities.User;
import net.vys.collection.entities.UserComic;
import net.vys.collection.exceptions.ComicAlreadyInCollectionException;
import net.vys.collection.exceptions.ComicNotFoundException;
import net.vys.collection.exceptions.UserNotFoundException;
import net.vys.collection.mapper.ComicMapper;
import net.vys.collection.repositories.ComicRepository;
import net.vys.collection.repositories.UserComicRepository;
import net.vys.collection.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import java.util.List;

@Service
public class UserComicServiceManager implements UserComicService {

    private final UserComicRepository userComicRepository;
    private final ComicRepository comicRepository;
    private final UserRepository userRepository;
    private final ComicMapper comicMapper;

    public UserComicServiceManager(UserComicRepository userComicRepository,
                                   ComicRepository comicRepository,
                                   UserRepository userRepository,
                                   ComicMapper comicMapper) {
        this.userComicRepository = userComicRepository;
        this.comicRepository = comicRepository;
        this.userRepository = userRepository;
        this.comicMapper = comicMapper;
    }

    private UserComicResponseDTO toDTO(UserComic userComic) {
        return new UserComicResponseDTO(
                userComic.getId(),
                comicMapper.toComicResponseDTO(userComic.getComic()),
                userComic.getStatus()
        );
    }

    @Override
    @Transactional
    public UserComicResponseDTO addToCollection(Long comicId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        Comic comic = comicRepository.findById(comicId)
                .orElseThrow(() -> new ComicNotFoundException(comicId));

        if (userComicRepository.existsByUserAndComicId(user, comicId)) {
            throw new ComicAlreadyInCollectionException(comic.getTitle());
        }

        UserComic userComic = new UserComic();
        userComic.setUser(user);
        userComic.setComic(comic);

        return toDTO(userComicRepository.save(userComic));
    }

    @Override
    @Transactional
    public void removeFromCollection(Long comicId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        UserComic userComic = userComicRepository.findByUserAndComicId(user, comicId)
                .orElseThrow(() -> new ComicNotFoundException(comicId));

        userComicRepository.delete(userComic);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserComicResponseDTO> getCollection(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return userComicRepository.findByUser(user)
            .stream()
            .map(this::toDTO)
            .toList();
    }
}