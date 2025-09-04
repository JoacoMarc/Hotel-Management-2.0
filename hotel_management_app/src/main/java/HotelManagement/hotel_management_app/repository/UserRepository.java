package HotelManagement.hotel_management_app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import HotelManagement.hotel_management_app.entity.User;
import HotelManagement.hotel_management_app.entity.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
    // Búsqueda por email (para autenticación)
    Optional<User> findByEmail(String email);
    
    // Verificar si existe email
    boolean existsByEmail(String email);
    
    // Verificar si existe número de documento (para huéspedes)
    boolean existsByDocumentNumber(String documentNumber);
    
    // Búsquedas por rol
    List<User> findByRole(UserRole role);
    
    // Empleados por hotel
    List<User> findByHotelIdAndRoleNot(UUID hotelId, UserRole role);
    
    // Búsquedas específicas para empleados
    List<User> findByRoleNotAndHotelId(UserRole role, UUID hotelId);
    
    // Búsqueda por código de empleado
    Optional<User> findByEmployeeCode(String employeeCode);
    
    // Verificar si existe código de empleado
    boolean existsByEmployeeCode(String employeeCode);
}

