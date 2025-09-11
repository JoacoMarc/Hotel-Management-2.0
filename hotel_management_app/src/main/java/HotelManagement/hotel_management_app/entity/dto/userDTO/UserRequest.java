package HotelManagement.hotel_management_app.entity.dto.userDTO;

import java.time.LocalDate;
import java.util.UUID;

import HotelManagement.hotel_management_app.entity.UserRole;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequest {
    
    private UUID id; // Opcional para creación
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String name;
    
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String surname;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email inválido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email;
    
    @Size(min = 8, max = 100, message = "La contraseña debe tener entre 8 y 100 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$|^$", 
             message = "La contraseña debe contener al menos una mayúscula, una minúscula y un número")
    private String password; // Opcional para actualización
    
    @Pattern(regexp = "^[+]?[0-9\\-\\s\\(\\)]{7,20}$|^$", 
             message = "Formato de teléfono inválido")
    private String phone;
    
    @NotNull(message = "El rol es obligatorio")
    private UserRole role;
    
    // Campos específicos para empleados (solo si role != GUEST)
    @Size(min = 3, max = 20, message = "El código de empleado debe tener entre 3 y 20 caracteres")
    @Pattern(regexp = "^[A-Z0-9]+$|^$", 
             message = "El código de empleado solo puede contener letras mayúsculas y números")
    private String employeeCode;
    
    private LocalDate hireDate;
    private UUID hotelId;
    
    // Campos específicos para huéspedes (solo si role == GUEST)
    @Pattern(regexp = "DNI|PASSPORT|CEDULA|ID_CARD|^$", 
             message = "Tipo de documento válido: DNI, PASSPORT, CEDULA, ID_CARD")
    private String documentType;
    
    @Size(min = 5, max = 20, message = "El número de documento debe tener entre 5 y 20 caracteres")
    private String documentNumber;
    
    @Size(min = 2, max = 50, message = "La nacionalidad debe tener entre 2 y 50 caracteres")
    private String nationality;
    
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate birthDate;
}
