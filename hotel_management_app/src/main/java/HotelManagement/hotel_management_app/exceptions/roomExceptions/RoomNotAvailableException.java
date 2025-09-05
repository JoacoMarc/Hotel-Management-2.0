package HotelManagement.hotel_management_app.exceptions.roomExceptions;

public class RoomNotAvailableException extends RuntimeException {
    public RoomNotAvailableException() {
        super("Habitación no disponible");
    }
    
    public RoomNotAvailableException(String message) {
        super(message);
    }
}
