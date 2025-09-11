package HotelManagement.hotel_management_app.validation;

import HotelManagement.hotel_management_app.entity.dto.bookingDTO.BookingRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, BookingRequest> {

    @Override
    public void initialize(ValidDateRange constraintAnnotation) {
        // Inicialización si es necesaria
    }

    @Override
    public boolean isValid(BookingRequest bookingRequest, ConstraintValidatorContext context) {
        if (bookingRequest == null || 
            bookingRequest.getCheckInDate() == null || 
            bookingRequest.getCheckOutDate() == null) {
            return true; // Otras validaciones se encargarán de campos null
        }

        boolean isValid = bookingRequest.getCheckOutDate().isAfter(bookingRequest.getCheckInDate());
        
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                "La fecha de check-out debe ser posterior a la fecha de check-in")
                .addPropertyNode("checkOutDate")
                .addConstraintViolation();
        }
        
        return isValid;
    }
}
