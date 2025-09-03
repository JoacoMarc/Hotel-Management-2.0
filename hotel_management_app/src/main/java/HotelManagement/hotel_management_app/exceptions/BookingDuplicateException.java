package HotelManagement.hotel_management_app.exceptions;

public class BookingDuplicateException extends RuntimeException {
    public BookingDuplicateException() {
        super("Booking already exists for this guest and dates");
    }
    
    public BookingDuplicateException(String message) {
        super(message);
    }
}
