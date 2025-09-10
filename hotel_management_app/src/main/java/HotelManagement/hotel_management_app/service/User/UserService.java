package HotelManagement.hotel_management_app.service.User;

import java.util.List;
import java.util.UUID;

import HotelManagement.hotel_management_app.entity.User;
import HotelManagement.hotel_management_app.entity.UserRole;

public interface UserService {
    
    // CRUD básico
    List<User> getAllUsers();
    User getUserById(UUID id);
    User updateUser(UUID id, User user);
    void deleteUser(UUID id);
    
    // Búsquedas por rol
    List<User> getUsersByRole(UserRole role);
    List<User> getEmployeesByHotel(UUID hotelId);
    
    // Búsquedas específicas
    User getUserByEmail(String email);
    User getUserByEmployeeCode(String employeeCode);
    
    // Búsqueda con filtros múltiples
    List<User> searchUsers(String email, String name, String surname, 
                          UserRole role, String employeeCode, String documentNumber);
    
    // Verificaciones
    boolean existsByEmail(String email);
    boolean existsByEmployeeCode(String employeeCode);
    boolean existsByDocumentNumber(String documentNumber);
}

