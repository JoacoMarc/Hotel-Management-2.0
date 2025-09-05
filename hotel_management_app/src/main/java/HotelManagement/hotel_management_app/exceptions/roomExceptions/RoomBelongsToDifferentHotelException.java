package HotelManagement.hotel_management_app.exceptions.roomExceptions;

public class RoomBelongsToDifferentHotelException extends RuntimeException {
    public RoomBelongsToDifferentHotelException() {
        super("La habitación no pertenece a este hotel");
    }
    
    public RoomBelongsToDifferentHotelException(String message) {
        super(message);
    }
}
