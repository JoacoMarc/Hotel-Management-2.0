package HotelManagement.hotel_management_app.entity.dto.roomDTO;

import java.util.UUID;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RoomRequest {
    
    private UUID id; // Opcional para creación
    
    @NotBlank(message = "El número de habitación es obligatorio")
    @Size(min = 1, max = 10, message = "El número de habitación debe tener entre 1 y 10 caracteres")
    private String roomNumber;
    
    @NotBlank(message = "El tipo de habitación es obligatorio")
    @Pattern(regexp = "SIMPLE|DOUBLE|SUITE|DELUXE|PRESIDENTIAL", 
             message = "Tipo de habitación válido: SIMPLE, DOUBLE, SUITE, DELUXE, PRESIDENTIAL")
    private String roomType;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @DecimalMax(value = "99999.99", message = "El precio no puede exceder 99,999.99")
    @NotNull(message = "El precio de la habitación es obligatorio")
    private Double roomPrice;
    
    @Min(value = 1, message = "La capacidad mínima es 1 persona")
    @Max(value = 20, message = "La capacidad máxima es 20 personas")
    @NotNull(message = "La capacidad de la habitación es obligatoria")
    private Integer roomCapacity;
    
    @NotNull(message = "La disponibilidad de la habitación es obligatoria")
    private Boolean roomAvailability;
    
    @NotNull(message = "El ID del hotel es obligatorio")
    private UUID hotelId;
}
