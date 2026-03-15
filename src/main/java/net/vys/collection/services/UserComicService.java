package net.vys.collection.services;

import net.vys.collection.entities.UserComic;

import java.util.List;

public interface UserComicService {

    UserComic addToCollection(Long comicId, String username);

    void removeFromCollection(Long comicId, String username);

    List<UserComic> getCollection(String username);
}