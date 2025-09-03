package HotelManagement.hotel_management_app.service.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import HotelManagement.hotel_management_app.entity.Employee;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(UUID id);
    Employee createEmployee(Employee employee);
    Employee updateEmployee(UUID id, Employee employee);
    Employee deleteEmployee(UUID id);
    Employee deleteEmployeeByEmail(String email); // DELETE por email
    List<Employee> getEmployeesByHotelId(UUID hotelId);
    List<Employee> getEmployeesByEmployeeCode(String employeeCode);
    List<Employee> getEmployeesByRole(String role);
    // Método de búsqueda con múltiples filtros opcionales
    List<Employee> searchEmployees(String name, String surname, String email, LocalDate hireDate, String role);
}