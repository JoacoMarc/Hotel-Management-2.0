package HotelManagement.hotel_management_app.service.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import HotelManagement.hotel_management_app.entity.Employee;
import HotelManagement.hotel_management_app.entity.EmployeeRole;
import HotelManagement.hotel_management_app.entity.Hotel;
import HotelManagement.hotel_management_app.repository.EmployeeRepository;
import HotelManagement.hotel_management_app.repository.HotelRepository;
import HotelManagement.hotel_management_app.exceptions.EmployeeNotFoundException;
import HotelManagement.hotel_management_app.exceptions.EmployeeDuplicateException;
import HotelManagement.hotel_management_app.exceptions.HotelNotFoundException;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private HotelRepository hotelRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(UUID id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException());
    }

    public Employee createEmployee(Employee employee) throws EmployeeDuplicateException {
        // Validar que el hotel existe
        if (employee.getHotel() != null && employee.getHotel().getId() != null) {
            hotelRepository.findById(employee.getHotel().getId())
                .orElseThrow(() -> new HotelNotFoundException());
        }
        
        // Validar duplicado por email
        List<Employee> existingByEmail = employeeRepository.findByEmail(employee.getEmail());
        if (!existingByEmail.isEmpty()) {
            throw new EmployeeDuplicateException("Employee with this email already exists");
        }
        
        // Validar duplicado por código de empleado
        List<Employee> existingByCode = employeeRepository.findByEmployeeCode(employee.getEmployeeCode());
        if (!existingByCode.isEmpty()) {
            throw new EmployeeDuplicateException("Employee with this code already exists");
        }
        
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(UUID id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException());
        existingEmployee.setName(employee.getName());
        existingEmployee.setSurname(employee.getSurname());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setPhone(employee.getPhone());
        existingEmployee.setEmployeeCode(employee.getEmployeeCode());
        existingEmployee.setHireDate(employee.getHireDate());
        existingEmployee.setRole(employee.getRole());
        
        // Actualizar hotel si se proporciona
        if (employee.getHotel() != null) {
            existingEmployee.setHotel(employee.getHotel());
        }
        
        return employeeRepository.save(existingEmployee);
    }

    public Employee deleteEmployee(UUID id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException());
        employeeRepository.deleteById(id);
        return employee;
    }
    
    // DELETE por email (alternativo)
    public Employee deleteEmployeeByEmail(String email) {
        System.out.println("DEBUG: Intentando eliminar empleado por email: " + email);
        
        List<Employee> employees = employeeRepository.findByEmail(email);
        if (employees.isEmpty()) {
            System.out.println("DEBUG: No se encontró empleado con email: " + email);
            throw new EmployeeNotFoundException();
        }
        
        Employee employee = employees.get(0); // Tomar el primero (email es único)
        System.out.println("DEBUG: Empleado encontrado por email: " + employee.getName() + " " + employee.getSurname());
        System.out.println("DEBUG: ID del empleado: " + employee.getId());
        
        // Eliminar usando el objeto encontrado
        employeeRepository.delete(employee);
        System.out.println("DEBUG: Empleado eliminado por email exitosamente");
        
        return employee;
    }

    public List<Employee> getEmployeesByHotelId(UUID hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new HotelNotFoundException());
        return employeeRepository.findByHotel(hotel);
    }

    public List<Employee> getEmployeesByEmployeeCode(String employeeCode) {
        return employeeRepository.findByEmployeeCode(employeeCode);
    }
    
    public List<Employee> getEmployeesByRole(String role) {
        try {
            EmployeeRole employeeRole = EmployeeRole.valueOf(role.toUpperCase());
            return employeeRepository.findByRole(employeeRole);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid employee role: " + role);
        }
    }
    
    @Override
    public List<Employee> searchEmployees(String name, String surname, String email, LocalDate hireDate, String role) {
        EmployeeRole employeeRole = null;
        if (role != null) {
            try {
                employeeRole = EmployeeRole.valueOf(role.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid employee role: " + role);
            }
        }
        return employeeRepository.searchEmployees(name, surname, email, hireDate, employeeRole);
    }
}