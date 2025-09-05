package HotelManagement.hotel_management_app.exceptions.hotelExceptions;

public class HotelDuplicateException extends RuntimeException {
    public HotelDuplicateException() {
        super("Hotel already exists with this name");
    }
    
    public HotelDuplicateException(String message) {
        super(message);
    }
}
