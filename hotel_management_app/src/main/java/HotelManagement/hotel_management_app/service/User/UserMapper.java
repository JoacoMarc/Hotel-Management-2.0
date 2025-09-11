package HotelManagement.hotel_management_app.service.user;

import org.springframework.stereotype.Component;

import HotelManagement.hotel_management_app.entity.User;
import HotelManagement.hotel_management_app.entity.dto.userDTO.UserResponse;

@Component
public class UserMapper {
    
    public UserResponse toUserResponse(User user) {
        UserResponse.UserResponseBuilder builder = UserResponse.builder()
            .id(user.getId())
            .name(user.getName())
            .surname(user.getSurname())
            .email(user.getEmail())
            .phone(user.getPhone())
            .role(user.getRole());
        
        // Si es empleado, incluir datos de empleado
        if (user.isEmployee()) {
            builder.employeeCode(user.getEmployeeCode())
                   .hireDate(user.getHireDate());
            
            if (user.getHotel() != null) {
                builder.hotelId(user.getHotel().getId())
                       .hotelName(user.getHotel().getName());
            }
        }
        
        // Si es huésped, incluir datos de huésped
        if (user.isGuest()) {
            builder.documentType(user.getDocumentType())
                   .documentNumber(user.getDocumentNumber())
                   .nationality(user.getNationality())
                   .birthDate(user.getBirthDate());
        }
        
        return builder.build();
    }
}

