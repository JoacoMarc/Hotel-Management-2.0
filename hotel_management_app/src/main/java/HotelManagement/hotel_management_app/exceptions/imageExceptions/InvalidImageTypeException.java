package HotelManagement.hotel_management_app.exceptions.imageExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidImageTypeException extends RuntimeException {
    
    public InvalidImageTypeException(String message) {
        super(message);
    }
    
    public InvalidImageTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
