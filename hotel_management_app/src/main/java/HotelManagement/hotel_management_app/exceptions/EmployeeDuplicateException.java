package HotelManagement.hotel_management_app.exceptions;

public class EmployeeDuplicateException extends RuntimeException {
    public EmployeeDuplicateException() {
        super("Employee already exists with this employee code");
    }
    
    public EmployeeDuplicateException(String message) {
        super(message);
    }
}
