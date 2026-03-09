package net.vys.collection.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Excepción base para todos los errores de negocio de la aplicación.
 * Lleva el HTTP status y un código interno para que el cliente
 * pueda identificar el tipo de error de forma programática.
 */
public abstract class ApplicationException extends RuntimeException {

    private final HttpStatus status;
    private final String errorCode;

    protected ApplicationException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.status    = status;
        this.errorCode = errorCode;
    }

    public HttpStatus getStatus()   { return status; }
    public String getErrorCode()    { return errorCode; }
}