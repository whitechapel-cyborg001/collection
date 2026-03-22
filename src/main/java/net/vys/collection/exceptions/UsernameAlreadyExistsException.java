package net.vys.collection.exceptions;

import org.springframework.http.HttpStatus;


/**
 * Se produce cuando se intenta registrar un nuevo usuario con un nombre de usuario que ya existe en la base de datos.
 * Produce una respuesta HTTP 409 Conflict.
 */
public class UsernameAlreadyExistsException extends ApplicationException {

    public UsernameAlreadyExistsException(String username) {
        super(
            String.format("El usuario '%s' ya existe", username),
            HttpStatus.CONFLICT,
            "USERNAME_ALREADY_EXISTS"
        );
    }
}