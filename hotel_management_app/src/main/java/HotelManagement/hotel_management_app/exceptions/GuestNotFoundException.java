package HotelManagement.hotel_management_app.exceptions;

public class GuestNotFoundException extends RuntimeException {
    public GuestNotFoundException() {
        super("Guest not found");
    }
    
    public GuestNotFoundException(String message) {
        super(message);
    }
}
