package HotelManagement.hotel_management_app.exceptions.imageExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ImageStorageException extends RuntimeException {
    
    public ImageStorageException(String message) {
        super(message);
    }
    
    public ImageStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
