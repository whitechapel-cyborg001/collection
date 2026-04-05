package net.vys.collection.services;

import net.vys.collection.dto.UserComicResponseDTO;
import net.vys.collection.entities.UserComic.CollectionStatus;

import java.util.List;

public interface UserComicService {
    UserComicResponseDTO addToCollection(Long comicId, String username, CollectionStatus status);
    void removeFromCollection(Long comicId, String username);
    List<UserComicResponseDTO> getCollection(String username);
}