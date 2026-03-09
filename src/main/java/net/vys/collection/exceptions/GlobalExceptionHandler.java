package net.vys.collection.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import net.vys.collection.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // -----------------------------------------------------------------------
    // 1. Excepciones de negocio propias (404, 409, 422, etc.)
    // -----------------------------------------------------------------------
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(
            ApplicationException ex, HttpServletRequest request) {

        log.warn("Business exception [{}] at {}: {}", ex.getErrorCode(), request.getRequestURI(), ex.getMessage());

        ErrorResponse body = ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(ex.getStatus().value())
                .errorCode(ex.getErrorCode())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(ex.getStatus()).body(body);
    }

    // -----------------------------------------------------------------------
    // 2. Errores de validación — @Valid en el @RequestBody
    // -----------------------------------------------------------------------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<ErrorResponse.FieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> new ErrorResponse.FieldError(e.getField(), e.getDefaultMessage()))
                .toList();

        log.warn("Validation failed at {}: {} field error(s)", request.getRequestURI(), fieldErrors.size());

        ErrorResponse body = ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .errorCode("VALIDATION_ERROR")
                .message("Los datos enviados no son válidos")
                .path(request.getRequestURI())
                .fieldErrors(fieldErrors)
                .build();

        return ResponseEntity.badRequest().body(body);
    }

    // -----------------------------------------------------------------------
    // 3. Tipo incorrecto en @PathVariable (ej: /authors/abc en lugar de Long)
    // -----------------------------------------------------------------------
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {

        String message = String.format(
                "El parámetro '%s' debe ser de tipo %s",
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconocido"
        );

        log.warn("Type mismatch at {}: {}", request.getRequestURI(), message);

        ErrorResponse body = ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .errorCode("INVALID_PARAMETER_TYPE")
                .message(message)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(body);
    }

    // -----------------------------------------------------------------------
    // 4. Fallback — cualquier error no controlado (500)
    // -----------------------------------------------------------------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(
            Exception ex, HttpServletRequest request) {

        // Log con stack trace completo para debugging interno
        log.error("Unexpected error at {}: {}", request.getRequestURI(), ex.getMessage(), ex);

        ErrorResponse body = ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorCode("INTERNAL_SERVER_ERROR")
                // Nunca exponemos el mensaje interno al cliente
                .message("Ocurrió un error inesperado. Por favor intenta más tarde.")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.internalServerError().body(body);
    }
}