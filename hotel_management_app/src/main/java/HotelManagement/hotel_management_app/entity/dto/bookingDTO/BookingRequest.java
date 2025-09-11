package HotelManagement.hotel_management_app.entity.dto.bookingDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import HotelManagement.hotel_management_app.validation.ValidDateRange;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@ValidDateRange
public class BookingRequest {
    
    private UUID id; // Opcional para creación, requerido para actualización
    
    @NotNull(message = "La fecha de check-in es obligatoria")
    @FutureOrPresent(message = "La fecha de check-in debe ser presente o futura")
    private LocalDate checkInDate;
    
    @NotNull(message = "La fecha de check-out es obligatoria")
    @Future(message = "La fecha de check-out debe ser futura")
    private LocalDate checkOutDate;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio total debe ser mayor a 0")
    @NotNull(message = "El precio total es obligatorio")
    private Double totalPrice;
    
    @Pattern(regexp = "CONFIRMED|PENDING|CANCELLED", 
             message = "El estado debe ser: CONFIRMED, PENDING o CANCELLED")
    private String status;
    
    @NotEmpty(message = "Debe especificar al menos un usuario")
    @Size(min = 1, max = 10, message = "Máximo 10 usuarios por reserva")
    private List<@NotNull(message = "Los IDs de usuario no pueden ser null") UUID> userIds;
    
    @NotNull(message = "El ID del hotel es obligatorio")
    private UUID hotelId;
    
    @NotEmpty(message = "Debe especificar al menos una habitación")
    @Size(min = 1, max = 5, message = "Máximo 5 habitaciones por reserva")
    private List<@NotNull(message = "Los IDs de habitación no pueden ser null") UUID> roomIds;
}
