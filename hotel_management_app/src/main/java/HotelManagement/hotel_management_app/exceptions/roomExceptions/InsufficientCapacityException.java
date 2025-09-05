package HotelManagement.hotel_management_app.exceptions.roomExceptions;

public class InsufficientCapacityException extends RuntimeException {
    public InsufficientCapacityException() {
        super("Capacidad insuficiente");
    }
    
    public InsufficientCapacityException(String message) {
        super(message);
    }
}
