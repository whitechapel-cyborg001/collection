package net.vys.collection.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Se lanza cuando se busca un Author que no existe en la base de datos.
 * Produce una respuesta HTTP 404 Not Found.
 */
public class AuthorNotFoundException extends ApplicationException {

    public AuthorNotFoundException(Long id) {
        super(
            String.format("Author con id '%d' no encontrado", id),
            HttpStatus.NOT_FOUND,
            "AUTHOR_NOT_FOUND"
        );
    }
}