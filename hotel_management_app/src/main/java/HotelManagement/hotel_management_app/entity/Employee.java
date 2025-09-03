package HotelManagement.hotel_management_app.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;
    
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "employee_code", unique = true, nullable = false)
    private String employeeCode;
    
    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private EmployeeRole role;
}
