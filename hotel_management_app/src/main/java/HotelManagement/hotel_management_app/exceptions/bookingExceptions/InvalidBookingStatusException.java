package HotelManagement.hotel_management_app.exceptions.bookingExceptions;

public class InvalidBookingStatusException extends RuntimeException {
    public InvalidBookingStatusException() {
        super("Estado de reserva inválido");
    }
    
    public InvalidBookingStatusException(String message) {
        super(message);
    }
}
