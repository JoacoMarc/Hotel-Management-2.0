package HotelManagement.hotel_management_app.exceptions;

public class UserDuplicateException extends RuntimeException {
    public UserDuplicateException() {
        super("Usuario ya existe");
    }
    
    public UserDuplicateException(String message) {
        super(message);
    }
}

