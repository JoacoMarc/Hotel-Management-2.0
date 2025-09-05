package HotelManagement.hotel_management_app.exceptions.roomExceptions;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException() {
        super("Room not found");
    }
    
    public RoomNotFoundException(String message) {
        super(message);
    }
}
