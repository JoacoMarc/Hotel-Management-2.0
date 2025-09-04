package HotelManagement.hotel_management_app.controllers.auth;

import HotelManagement.hotel_management_app.entity.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRegisterRequest {

    private String name;
    private String surname;
    private String email;
    private String password;
    private String phone;
    private String employeeCode;
    private String hotelId; // UUID del hotel
    private UserRole role;
}
