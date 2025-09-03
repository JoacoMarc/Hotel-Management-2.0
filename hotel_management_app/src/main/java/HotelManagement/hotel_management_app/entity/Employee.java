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
    
    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;
    
    @Column(name = "email", unique = true)
    private String email;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "employee_code", unique = true)
    private String employeeCode;
    
    @Column(name = "hire_date")
    private LocalDate hireDate;
    
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
