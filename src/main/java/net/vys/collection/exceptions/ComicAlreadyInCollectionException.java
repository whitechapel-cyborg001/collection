package net.vys.collection.exceptions;

import org.springframework.http.HttpStatus;

public class ComicAlreadyInCollectionException extends ApplicationException {
    public ComicAlreadyInCollectionException(String comicTitle) {
        super(
            String.format("El cómic '%s' ya está en la colección", comicTitle),
            HttpStatus.CONFLICT,
            "COMIC_ALREADY_IN_COLLECTION"
        );
    }
}
