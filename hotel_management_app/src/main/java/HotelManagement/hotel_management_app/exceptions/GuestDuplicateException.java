package HotelManagement.hotel_management_app.exceptions;

public class GuestDuplicateException extends RuntimeException {
    public GuestDuplicateException() {
        super("Guest already exists with this document number");
    }
    
    public GuestDuplicateException(String message) {
        super(message);
    }
}
