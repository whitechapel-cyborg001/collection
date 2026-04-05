package net.vys.collection.dto;

import net.vys.collection.entities.UserComic.CollectionStatus;

public class UserComicResponseDTO {

    private Long id;
    private ComicResponseDTO comic;
    private CollectionStatus status;

    public UserComicResponseDTO(Long id, ComicResponseDTO comic, CollectionStatus status) {
        this.id = id;
        this.comic = comic;
        this.status = status;
    }

    public Long getId() { return id; }
    public ComicResponseDTO getComic() { return comic; }
    public CollectionStatus getStatus() { return status; }
}