package HotelManagement.hotel_management_app.exceptions.roomExceptions;

public class RoomHasActiveBookingsException extends RuntimeException {
    public RoomHasActiveBookingsException() {
        super("No se puede eliminar la habitación porque tiene reservas activas");
    }
    
    public RoomHasActiveBookingsException(String message) {
        super(message);
    }
}
