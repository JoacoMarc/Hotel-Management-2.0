package HotelManagement.hotel_management_app.exceptions.roomExceptions;

public class RoomDuplicateException extends RuntimeException {
    public RoomDuplicateException() {
        super("Room already exists with this number in the same hotel");
    }
    
    public RoomDuplicateException(String message) {
        super(message);
    }
}
