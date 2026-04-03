package net.vys.collection.exceptions;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException(String username) {
        super(
            String.format("Usuario con username '%s' no encontrado", username),
            HttpStatus.NOT_FOUND,
            "USER_NOT_FOUND"
        );
    }
}