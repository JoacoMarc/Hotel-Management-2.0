package HotelManagement.hotel_management_app.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import HotelManagement.hotel_management_app.entity.Employee;
import HotelManagement.hotel_management_app.entity.Hotel;
import HotelManagement.hotel_management_app.entity.dto.EmployeeRequest;
import HotelManagement.hotel_management_app.service.Employee.EmployeeService;
import HotelManagement.hotel_management_app.service.Hotel.HotelService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private HotelService hotelService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{employeeId}")
    public Employee getEmployeeById(@PathVariable UUID employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @PostMapping
    public Employee createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        // Convertir EmployeeRequest a Employee
        Employee employee = new Employee();
        employee.setName(employeeRequest.getName());
        employee.setSurname(employeeRequest.getSurname());
        employee.setEmail(employeeRequest.getEmail());
        employee.setPassword(employeeRequest.getPassword());
        employee.setPhone(employeeRequest.getPhone());
        employee.setEmployeeCode(employeeRequest.getEmployeeCode());
        employee.setHireDate(employeeRequest.getHireDate());
        
        // Obtener y asignar el hotel real
        if (employeeRequest.getHotelId() != null) {
            Hotel hotel = hotelService.getHotelById(employeeRequest.getHotelId());
            employee.setHotel(hotel);
        }
        
        return employeeService.createEmployee(employee);
    }

    @PutMapping("/{employeeId}")
    public Employee updateEmployee(@PathVariable UUID employeeId, @RequestBody Employee employee) {
        return employeeService.updateEmployee(employeeId, employee);
    }

    @DeleteMapping("/{employeeId}")
    public Employee deleteEmployee(@PathVariable UUID employeeId) {
        return employeeService.deleteEmployee(employeeId);
    }
    
    @DeleteMapping("/email/{email}")
    public Employee deleteEmployeeByEmail(@PathVariable String email) {
        return employeeService.deleteEmployeeByEmail(email);
    }

    
    @GetMapping("/hotel/{hotelId}")
    public List<Employee> getEmployeesByHotelId(@PathVariable UUID hotelId) {
        return employeeService.getEmployeesByHotelId(hotelId);
    }

    @GetMapping("/code/{employeeCode}")
    public List<Employee> getEmployeesByEmployeeCode(@PathVariable String employeeCode) {
        return employeeService.getEmployeesByEmployeeCode(employeeCode);
    }

    @GetMapping("/hire-date")
    public List<Employee> getEmployeesByHireDate(@RequestParam String hireDate) {
        return employeeService.getEmployeesByHireDate(LocalDate.parse(hireDate));
    }

    @GetMapping("/surname/{surname}")
    public List<Employee> getEmployeesBySurname(@PathVariable String surname) {
        return employeeService.getEmployeesBySurname(surname);
    }

    @GetMapping("/name/{name}")
    public List<Employee> getEmployeesByName(@PathVariable String name) {
        return employeeService.getEmployeesByName(name);
    }

    @GetMapping("/email/{email}")
    public List<Employee> getEmployeesByEmail(@PathVariable String email) {
        return employeeService.getEmployeesByEmail(email);
    }
}