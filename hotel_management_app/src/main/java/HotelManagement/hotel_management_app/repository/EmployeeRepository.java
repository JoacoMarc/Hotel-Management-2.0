package HotelManagement.hotel_management_app.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import HotelManagement.hotel_management_app.entity.Employee;
import HotelManagement.hotel_management_app.entity.EmployeeRole;
import HotelManagement.hotel_management_app.entity.Hotel;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    
    // Buscar empleados por hotel
    List<Employee> findByHotel(Hotel hotel);
    List<Employee> findByHotelId(UUID hotelId);
    
    // Buscar empleados por código de empleado
    List<Employee> findByEmployeeCode(String employeeCode);
    
    // Buscar empleados por rol
    List<Employee> findByRole(EmployeeRole role);
    
    // Buscar empleados por email (único)
    List<Employee> findByEmail(String email);
    
    // Buscar empleados por nombre
    List<Employee> findByName(String name);
    List<Employee> findByNameContainingIgnoreCase(String name);
    
    // Buscar empleados por apellido
    List<Employee> findBySurname(String surname);
    List<Employee> findBySurnameContainingIgnoreCase(String surname);
    
    // Buscar empleados por nombre completo
    List<Employee> findByNameAndSurname(String name, String surname);
    
    // Buscar empleados por fecha de contratación
    List<Employee> findByHireDate(LocalDate hireDate);
    List<Employee> findByHireDateBetween(LocalDate startDate, LocalDate endDate);
    List<Employee> findByHireDateAfter(LocalDate date);
    List<Employee> findByHireDateBefore(LocalDate date);
    
    // Buscar empleados por teléfono
    List<Employee> findByPhone(String phone);
    
    // Búsqueda con múltiples filtros opcionales
    @Query("SELECT e FROM Employee e WHERE " +
           "(:name IS NULL OR e.name LIKE %:name%) AND " +
           "(:surname IS NULL OR e.surname LIKE %:surname%) AND " +
           "(:email IS NULL OR e.email = :email) AND " +
           "(:hireDate IS NULL OR e.hireDate = :hireDate) AND " +
           "(:role IS NULL OR e.role = :role)")
    List<Employee> searchEmployees(@Param("name") String name,
                                  @Param("surname") String surname,
                                  @Param("email") String email,
                                  @Param("hireDate") LocalDate hireDate,
                                  @Param("role") EmployeeRole role);
}
