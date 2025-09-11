package HotelManagement.hotel_management_app.exceptions.imageExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmptyImageFileException extends RuntimeException {
    
    public EmptyImageFileException(String message) {
        super(message);
    }
    
    public EmptyImageFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
