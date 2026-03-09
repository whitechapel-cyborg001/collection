package net.vys.collection.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Se lanza cuando se busca una Serie que no existe en la base de datos.
 * Produce una respuesta HTTP 404 Not Found.
 */
public class SerieNotFoundException extends ApplicationException {

    public SerieNotFoundException(Long id) {
        super(
            String.format("Serie con id '%d' no encontrada", id),
            HttpStatus.NOT_FOUND,
            "SERIE_NOT_FOUND"
        );
    }
}