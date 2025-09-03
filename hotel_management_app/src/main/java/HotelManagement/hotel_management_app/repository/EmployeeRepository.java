package HotelManagement.hotel_management_app.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import HotelManagement.hotel_management_app.entity.Employee;
import HotelManagement.hotel_management_app.entity.Hotel;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    
    // Buscar empleados por hotel
    List<Employee> findByHotel(Hotel hotel);
    List<Employee> findByHotelId(UUID hotelId);
    
    // Buscar empleados por código de empleado
    List<Employee> findByEmployeeCode(String employeeCode);
    
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
}
