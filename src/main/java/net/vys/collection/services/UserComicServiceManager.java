package net.vys.collection.services;

import net.vys.collection.entities.Comic;
import net.vys.collection.entities.User;
import net.vys.collection.entities.UserComic;
import net.vys.collection.exceptions.ComicNotFoundException;
import net.vys.collection.repositories.ComicRepository;
import net.vys.collection.repositories.UserComicRepository;
import net.vys.collection.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserComicServiceManager implements UserComicService {

    private final UserComicRepository userComicRepository;
    private final ComicRepository comicRepository;
    private final UserRepository userRepository;

    public UserComicServiceManager(UserComicRepository userComicRepository,
                                   ComicRepository comicRepository,
                                   UserRepository userRepository) {
        this.userComicRepository = userComicRepository;
        this.comicRepository = comicRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserComic addToCollection(Long comicId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Comic comic = comicRepository.findById(comicId)
                .orElseThrow(() -> new ComicNotFoundException(comicId));

        if (userComicRepository.existsByUserAndComicId(user, comicId)) {
            throw new RuntimeException("El cómic ya está en tu colección");
        }

        UserComic userComic = new UserComic();
        userComic.setUser(user);
        userComic.setComic(comic);

        return userComicRepository.save(userComic);
    }

    @Override
    public void removeFromCollection(Long comicId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        UserComic userComic = userComicRepository.findByUserAndComicId(user, comicId)
                .orElseThrow(() -> new RuntimeException("El cómic no está en tu colección"));

        userComicRepository.delete(userComic);
    }

    @Override
    public java.util.List<UserComic> getCollection(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return userComicRepository.findByUser(user);
    }
}