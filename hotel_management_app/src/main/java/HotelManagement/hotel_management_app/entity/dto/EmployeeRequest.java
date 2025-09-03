package HotelManagement.hotel_management_app.entity.dto;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;
import HotelManagement.hotel_management_app.entity.EmployeeRole;

@Data
public class EmployeeRequest {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phone;
    private String employeeCode;
    private LocalDate hireDate;
    private UUID hotelId;
    private EmployeeRole role;
}
