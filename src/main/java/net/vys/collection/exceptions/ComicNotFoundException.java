package net.vys.collection.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Se lanza cuando se busca un Comic que no existe en la base de datos.
 * Produce una respuesta HTTP 404 Not Found.
 */
public class ComicNotFoundException extends ApplicationException {

    public ComicNotFoundException(Long id) {
        super(
            String.format("Comic con id '%d' no encontrado", id),
            HttpStatus.NOT_FOUND,
            "COMIC_NOT_FOUND"
        );
    }
}