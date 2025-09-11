package HotelManagement.hotel_management_app.entity.dto.hotelDTO;

import java.util.UUID;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class HotelRequest {
    
    private UUID id; // Opcional para creación
    
    @NotBlank(message = "El nombre del hotel es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;
    
    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 5, max = 200, message = "La dirección debe tener entre 5 y 200 caracteres")
    private String address;
    
    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[+]?[0-9\\-\\s\\(\\)]{7,20}$", 
             message = "Formato de teléfono inválido")
    private String phone;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email inválido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email;
    
    @Size(max = 100, message = "El sitio web no puede exceder 100 caracteres")
    @Pattern(regexp = "^(https?://)?(www\\.)?[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,}(/.*)?$|^$",
             message = "Formato de URL inválido")
    private String website;
    
    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    @NotNull(message = "La calificación es obligatoria")
    private Integer rating;
    
    @NotBlank(message = "El país es obligatorio")
    @Size(min = 2, max = 50, message = "El país debe tener entre 2 y 50 caracteres")
    private String country;
    
    @NotBlank(message = "La ciudad es obligatoria")
    @Size(min = 2, max = 50, message = "La ciudad debe tener entre 2 y 50 caracteres")
    private String city;
    
    @Size(max = 50, message = "El estado no puede exceder 50 caracteres")
    private String state;
    
    @Pattern(regexp = "^[0-9A-Za-z\\-]{3,10}$|^$", 
             message = "Código postal inválido")
    private String zipCode;
    
    @Pattern(regexp = "LUXURY|BUSINESS|RESORT|BOUTIQUE|BUDGET|EXTENDED_STAY|^$", 
             message = "Tipo de hotel válido: LUXURY, BUSINESS, RESORT, BOUTIQUE, BUDGET, EXTENDED_STAY")
    private String hotelType;
}
