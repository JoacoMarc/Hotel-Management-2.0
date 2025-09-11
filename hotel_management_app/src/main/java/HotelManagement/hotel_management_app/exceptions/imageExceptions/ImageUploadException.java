package HotelManagement.hotel_management_app.exceptions.imageExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ImageUploadException extends RuntimeException {
    
    public ImageUploadException(String message) {
        super(message);
    }
    
    public ImageUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
