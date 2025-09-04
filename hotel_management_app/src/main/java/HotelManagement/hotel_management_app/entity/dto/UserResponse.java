package HotelManagement.hotel_management_app.entity.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import HotelManagement.hotel_management_app.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Solo incluir campos no-null
public class UserResponse {
    
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private UserRole role;
    
    // Campos específicos para empleados (solo si role != GUEST)
    private String employeeCode;
    private LocalDate hireDate;
    private UUID hotelId;
    private String hotelName;
    
    // Campos específicos para huéspedes (solo si role == GUEST)
    private String documentType;
    private String documentNumber;
    private String nationality;
    private LocalDate birthDate;
    
    // ELIMINADOS: métodos helper redundantes (el role ya indica el tipo)
}
