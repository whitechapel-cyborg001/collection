package net.vys.collection.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Se lanza cuando se busca un Publisher que no existe en la base de datos.
 * Produce una respuesta HTTP 404 Not Found.
 */
public class PublisherNotFoundException extends ApplicationException {

    public PublisherNotFoundException(Long id) {
        super(
            String.format("Publisher con id '%d' no encontrado", id),
            HttpStatus.NOT_FOUND,
            "PUBLISHER_NOT_FOUND"
        );
    }
}