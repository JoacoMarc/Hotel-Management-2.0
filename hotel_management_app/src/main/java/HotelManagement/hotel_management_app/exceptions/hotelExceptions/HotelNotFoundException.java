package HotelManagement.hotel_management_app.exceptions.hotelExceptions;

public class HotelNotFoundException extends RuntimeException {
    public HotelNotFoundException() {
        super("Hotel not found");
    }
    
    public HotelNotFoundException(String message) {
        super(message);
    }
}
