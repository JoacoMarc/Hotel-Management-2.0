package HotelManagement.hotel_management_app.exceptions.bookingExceptions;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException() {
        super("Booking not found");
    }
    
    public BookingNotFoundException(String message) {
        super(message);
    }
}
