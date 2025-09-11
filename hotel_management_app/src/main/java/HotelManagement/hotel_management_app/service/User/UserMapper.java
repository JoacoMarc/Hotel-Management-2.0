package HotelManagement.hotel_management_app.service.user;

import org.springframework.stereotype.Component;

import HotelManagement.hotel_management_app.entity.User;
import HotelManagement.hotel_management_app.entity.Hotel;
import HotelManagement.hotel_management_app.entity.dto.userDTO.UserRequest;
import HotelManagement.hotel_management_app.entity.dto.userDTO.UserResponse;

@Component
public class UserMapper {
    
    public User toEntity(UserRequest request, Hotel hotel) {
        if (request == null) {
            return null;
        }
        
        User user = User.builder()
                .id(request.getId())
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .phone(request.getPhone())
                .role(request.getRole())
                .employeeCode(request.getEmployeeCode())
                .hireDate(request.getHireDate())
                .hotel(hotel)
                .documentType(request.getDocumentType())
                .documentNumber(request.getDocumentNumber())
                .nationality(request.getNationality())
                .birthDate(request.getBirthDate())
                .build();
        
        // Set password only if provided (for creation)
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(request.getPassword());
        }
        
        return user;
    }
    
    public void updateEntity(User existing, UserRequest request) {
        if (request == null || existing == null) {
            return;
        }
        
        if (request.getName() != null) {
            existing.setName(request.getName());
        }
        if (request.getSurname() != null) {
            existing.setSurname(request.getSurname());
        }
        if (request.getEmail() != null) {
            existing.setEmail(request.getEmail());
        }
        if (request.getPhone() != null) {
            existing.setPhone(request.getPhone());
        }
        if (request.getRole() != null) {
            existing.setRole(request.getRole());
        }
        if (request.getEmployeeCode() != null) {
            existing.setEmployeeCode(request.getEmployeeCode());
        }
        if (request.getHireDate() != null) {
            existing.setHireDate(request.getHireDate());
        }
        if (request.getDocumentType() != null) {
            existing.setDocumentType(request.getDocumentType());
        }
        if (request.getDocumentNumber() != null) {
            existing.setDocumentNumber(request.getDocumentNumber());
        }
        if (request.getNationality() != null) {
            existing.setNationality(request.getNationality());
        }
        if (request.getBirthDate() != null) {
            existing.setBirthDate(request.getBirthDate());
        }
        
        // Only update password if provided
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            existing.setPassword(request.getPassword());
        }
        
        // Note: hotelId should be handled separately for security
    }
    
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

