package HotelManagement.hotel_management_app.exceptions.imageExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ImageSizeExceededException extends RuntimeException {
    
    public ImageSizeExceededException(String message) {
        super(message);
    }
    
    public ImageSizeExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
