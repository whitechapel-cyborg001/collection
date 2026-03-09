package net.vys.collection.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private Instant timestamp;
    private int status;
    private String errorCode;
    private String message;
    private String path;
    private List<FieldError> fieldErrors;

    // Constructor privado — usar builder
    private ErrorResponse() {}

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final ErrorResponse response = new ErrorResponse();

        public Builder timestamp(Instant timestamp) {
            response.timestamp = timestamp;
            return this;
        }
        public Builder status(int status) {
            response.status = status;
            return this;
        }
        public Builder errorCode(String errorCode) {
            response.errorCode = errorCode;
            return this;
        }
        public Builder message(String message) {
            response.message = message;
            return this;
        }
        public Builder path(String path) {
            response.path = path;
            return this;
        }
        public Builder fieldErrors(List<FieldError> fieldErrors) {
            response.fieldErrors = fieldErrors;
            return this;
        }
        public ErrorResponse build() {
            return response;
        }
    }

    // Getters
    public Instant getTimestamp()           { return timestamp; }
    public int getStatus()                  { return status; }
    public String getErrorCode()            { return errorCode; }
    public String getMessage()              { return message; }
    public String getPath()                 { return path; }
    public List<FieldError> getFieldErrors(){ return fieldErrors; }

    // ---- Clase interna para errores de validación por campo ----
    public static class FieldError {
        private final String field;
        private final String message;

        public FieldError(String field, String message) {
            this.field   = field;
            this.message = message;
        }

        public String getField()   { return field; }
        public String getMessage() { return message; }
    }
}